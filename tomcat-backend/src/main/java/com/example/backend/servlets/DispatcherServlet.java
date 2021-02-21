package com.example.backend.servlets;

import com.example.backend.RESTEntities.RequestEntity;
import com.example.backend.RESTEntities.RequestEntityProvider;
import com.example.backend.RESTEntities.ResponseEntity;
import com.example.backend.annotations.PathVariable;
import com.example.backend.annotations.RequestBody;
import com.example.backend.annotations.RequestMapping;
import com.example.backend.annotations.RequestPath;
import com.example.backend.constants.HttpMethod;
import com.example.backend.constants.HttpStatus;
import com.example.backend.controllers.CharacterController;
import com.example.backend.controllers.MovieController;
import com.example.backend.controllers.StarshipController;
import com.example.backend.repositories.EntityRepository;
import com.example.backend.utils.ControllerRegistry;
import com.example.backend.utils.RegexUtil;
import com.example.backend.utils.URLValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestPath
public class DispatcherServlet extends HttpServlet {


    private ControllerRegistry registry;

    @Override
    public void init() throws ServletException {
        super.init();
        registry=ControllerRegistry.getInstance();
        registry.register(MovieController.class);
        registry.register(CharacterController.class);
        registry.register(StarshipController.class);
    }

    public void registerController(Class<?> controllerClass){
        registry.register(controllerClass);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request,response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request,response);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (Class<?> controllerClass : registry.getControllerClasses()) {
            try {
                if (!canHandleRequest(controllerClass, request)) {
                    continue;
                }
                Object instantiatedController=controllerClass.getDeclaredConstructor(EntityRepository.class).newInstance(new EntityRepository());
                String controllerUri = instantiatedController.getClass().getAnnotation(RequestPath.class).value();
                List<Method> requestTypeMethods =
                        getSuitableMethods(controllerClass.getDeclaredMethods(), RequestMapping.class,request.getMethod());
                if (requestTypeMethods.isEmpty()){
                    response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
                    return;
                }
                Method methodToInvoke = getMethodToInvoke(requestTypeMethods,request, controllerUri);
                if (methodToInvoke==null){
                    response.setStatus(HttpStatus.NOT_FOUND.value());
                    return;
                }
                String requestUri = request.getRequestURI();
                String methodUri = getMethodUri(requestUri, controllerUri);
                String methodPlaceholders = methodToInvoke.getAnnotation(RequestMapping.class).value();
                List<String> methodPathVariables = getMethodPathVariables(methodToInvoke);
                RequestEntity entity = new RequestEntityProvider(request)
                        .createRequestEntity(methodPathVariables, getUriPathVariables(methodPlaceholders, methodUri, methodPathVariables));
                ResponseEntity<Object> result = invokeControllerMethod(methodToInvoke,instantiatedController,entity);
                response.setStatus(result.getStatus().value());
                if (result.getBody()!=null){
                    printResponse(response,result.getBody());
                }
            } catch (IllegalArgumentException | JsonMappingException e){
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                printResponse(response, e.getMessage());
            } catch (InstantiationException | IllegalAccessException |
                    InvocationTargetException | NoSuchMethodException |
                    IOException | IllegalStateException e) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                printResponse(response,e.getMessage());
            }
            return;
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    private boolean canHandleRequest(Class<?> controllerClass, HttpServletRequest request) throws IllegalArgumentException{
        String requestUri = request.getRequestURI();
        String controllerUri = getHandledPath(controllerClass);
            return requestUri.contains(controllerUri);
        }

    private String getHandledPath(Class<?> controllerClass) {
        return controllerClass.getAnnotation(RequestPath.class).value();
    }

    private List<Method> getSuitableMethods(Method[] classMethods, Class<? extends Annotation> annotation, String requestMethod){
        List<Method> annotatedMethods = getAnnotatedMethods(classMethods,annotation);
        annotatedMethods.removeIf(method -> !methodTypeMatches(method, requestMethod));
        return annotatedMethods;
    }

    private List<Method>  getAnnotatedMethods(Method[] classMethods, Class<? extends Annotation> annotation){
        return Stream.of(classMethods).filter(method -> method.isAnnotationPresent(annotation)).collect(Collectors.toList());
    }

    private Method getMethodToInvoke(List<Method> annotatedMethods, HttpServletRequest request, String controllerUri) throws IOException {
        for (Method method : annotatedMethods){
            if(!methodTypeMatches(method,request.getMethod())){
                continue;
            }
            String methodUri = method.getAnnotation(RequestMapping.class).value();
            Map<String, Class<?>> pathVariableTypesMap = getPathVariableTypesMap(method);
            String methodUriFromRequest = removeTrailingSlash(getMethodUri(request.getRequestURI(), controllerUri));
            if (URLValidator.isUrlValid(methodUriFromRequest, methodUri, pathVariableTypesMap)){
                return method;
            }
        }
        return null;
    }

    private Map<String, Class<?>> getPathVariableTypesMap (Method method){
        Map<String, Class<?>> resultMap = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(PathVariable.class)){
                resultMap.put(parameter.getAnnotation(PathVariable.class).value(),parameter.getType());
            }
        }
        return resultMap;
    }

    private String removeTrailingSlash(String url){
        if (url.endsWith("/")){
            return url.substring(0,url.length()-1);
        }
        return url;
    }

    private String getMethodUri(String requestUri, String controllerUri){
        if (requestUri!=null&&!requestUri.isEmpty()){
            return requestUri.replace(controllerUri, "");
        }
        throw new IllegalArgumentException("Request URI was null");

    }

    private List<String> getMethodPathVariables(Method method){
        List<String> pathVariables = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter:parameters){
            if (parameter.isAnnotationPresent(PathVariable.class)){
                pathVariables.add(parameter.getAnnotation(PathVariable.class).value());
            }
        }
        return pathVariables;
    }

    private Map<String, String> getUriPathVariables(String pathWithPlaceholders, String pathVariableString, List<String> methodPathVariables){
        String regex = RegexUtil.buildRegexString(pathWithPlaceholders, methodPathVariables);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pathVariableString);
        Map<String, String> uriPathVariables = new HashMap<>();
        if (matcher.find()){
            for (String pathVariable : methodPathVariables){
                uriPathVariables.put(pathVariable, matcher.group(pathVariable));
            }
        }
        return uriPathVariables;
    }

    private boolean methodTypeMatches(Method method, String methodFromRequest){
        HttpMethod methodType = method.getAnnotation(RequestMapping.class).method();
        HttpMethod requestMethod = HttpMethod.valueOf(methodFromRequest);
        return methodType.equals(requestMethod);
    }

    private ResponseEntity<Object> invokeControllerMethod(Method method, Object instantiatedObject, RequestEntity entity) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, JsonProcessingException {
        Parameter[] parameters = method.getParameters();
        Object[] parametersForMethodInvocation = setUpParametersForMethodInvocation(parameters,entity);
        Object result = method.invoke(instantiatedObject,parametersForMethodInvocation);
        if (result instanceof ResponseEntity){
            return (ResponseEntity<Object>) result;
        }
        throw new IllegalStateException("Invalid return entity type of controller method invocation.");
    }

    private Object[] setUpParametersForMethodInvocation(Parameter[] methodParameters, RequestEntity entity) throws JsonProcessingException {
        List<Object> resultParameters = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Parameter parameter : methodParameters){
            if (parameter.isAnnotationPresent(RequestBody.class)){
                Class<?> entityClass = parameter.getType();
                resultParameters.add(mapper.readValue(entity.getBody(),entityClass));
            }
            if (parameter.isAnnotationPresent(PathVariable.class)) {
                String pathVariableName = parameter.getAnnotation(PathVariable.class).value();
                String actualParameter = entity.getPathVariables().get(pathVariableName);
                resultParameters.add(mapper.convertValue(actualParameter, parameter.getType()));
            }
        }

        return resultParameters.toArray();
    }

    private <T> void printResponse(HttpServletResponse response, T controllerResponse) throws IOException{
        ObjectWriter writer = setDateFormatForObjectMapper().writer().withDefaultPrettyPrinter();
        writer.writeValue(response.getOutputStream(),controllerResponse);
    }

    private ObjectMapper setDateFormatForObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

}
