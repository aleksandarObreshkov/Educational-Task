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
import com.example.backend.errors.MethodNotAllowedException;
import repositories.EntityRepository;
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
            if (canHandleRequest(controllerClass, request)) {
                handleRequestWithController(request,response,controllerClass);
                return;
            }
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    private boolean canHandleRequest(Class<?> controllerClass, HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        String controllerUri = getHandledPath(controllerClass);
        return requestUri.contains(controllerUri);
    }

    private String getHandledPath(Class<?> controllerClass) {
        return controllerClass.getAnnotation(RequestPath.class).value();
    }

    private void handleRequestWithController(HttpServletRequest request, HttpServletResponse response, Class<?> controllerClass) throws IOException {
        try {
            Object instantiatedController = controllerClass.getDeclaredConstructor().newInstance();
            String controllerUri = instantiatedController.getClass().getAnnotation(RequestPath.class).value();
            Method methodToInvoke = getMethodToInvoke(controllerClass.getDeclaredMethods(), request, controllerUri);
            if (methodToInvoke == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }
            RequestEntity entity = buildRequestEntity(request, methodToInvoke, controllerUri);
            ResponseEntity<Object> result = invokeControllerMethod(methodToInvoke, instantiatedController, entity);
            respond(response, result.getStatus(), result.getBody());
        }catch(MethodNotAllowedException e){
            respond(response,HttpStatus.METHOD_NOT_ALLOWED,e.getMessage());
        } catch (IllegalArgumentException | JsonMappingException e){
            respond(response,HttpStatus.BAD_REQUEST,e.getMessage());
        } catch (InstantiationException | IllegalAccessException |
                InvocationTargetException | NoSuchMethodException |
                IOException | IllegalStateException e) {
            respond(response,HttpStatus.INTERNAL_SERVER_ERROR,e.getMessage());
        }
    }

    private Method getMethodToInvoke(Method[] controllerMethods, HttpServletRequest request, String controllerUri) throws IOException, MethodNotAllowedException {
        List<Method> requestTypeMethods =
                getSuitableMethods(controllerMethods, RequestMapping.class,request.getMethod());
        if (requestTypeMethods.isEmpty()){
            throw new MethodNotAllowedException("Method "+request.getMethod()+" is not allowed");
        }
        for (Method method : requestTypeMethods){
            String methodUri = method.getAnnotation(RequestMapping.class).value();
            Map<String, Class<?>> pathVariableTypesMap = getPathVariableTypesMap(method);
            String methodUriFromRequest = removeTrailingSlash(getMethodUri(request.getRequestURI(), controllerUri));
            if (URLValidator.isUrlValid(methodUriFromRequest, methodUri, pathVariableTypesMap)){
                return method;
            }
        }
        return null;
    }

    private List<Method> getSuitableMethods(Method[] classMethods, Class<? extends Annotation> annotation, String requestMethod){
        return getAnnotatedMethods(classMethods, annotation)
                .stream()
                .filter(method -> controllerMethodSupportsHttpRequestMethod(method, requestMethod))
                .collect(Collectors.toList());
    }

    private List<Method>  getAnnotatedMethods(Method[] classMethods, Class<? extends Annotation> annotation){
        return Stream.of(classMethods).filter(method -> method.isAnnotationPresent(annotation)).collect(Collectors.toList());
    }

    private boolean controllerMethodSupportsHttpRequestMethod(Method method, String httpMethodFromRequest){
        HttpMethod handleableHttpMethod = method.getAnnotation(RequestMapping.class).method();
        HttpMethod requestHttpMethod = HttpMethod.valueOf(httpMethodFromRequest);
        return handleableHttpMethod.equals(requestHttpMethod);
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

    private RequestEntity buildRequestEntity(HttpServletRequest request, Method methodToInvoke, String controllerUri) throws IOException {
        String methodUri = getMethodUri(request.getRequestURI(), controllerUri);
        String methodUriWithPlaceholders = methodToInvoke.getAnnotation(RequestMapping.class).value();
        List<String> methodPathVariables = getMethodPathVariables(methodToInvoke);
        return new RequestEntityProvider(request.getReader())
                .createRequestEntity(methodPathVariables, getPathVariableValues(methodUriWithPlaceholders, methodUri, methodPathVariables));
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

    private Map<String, String> getPathVariableValues(String uriWithPathVariablePlaceholders, String uri, List<String> pathVariablePlaceholders){
        String regex = RegexUtil.buildRegexString(uriWithPathVariablePlaceholders, pathVariablePlaceholders);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);
        Map<String, String> uriPathVariables = new HashMap<>();
        matcher.matches(); //needed to initialize matching
        for (String pathVariable : pathVariablePlaceholders) {
            uriPathVariables.put(pathVariable, matcher.group(pathVariable));
        }
        return uriPathVariables;
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

    private void respond(HttpServletResponse response, HttpStatus status, Object responseBody) throws IOException {
        response.setStatus(status.value());
        if (responseBody!=null){
            printResponse(response, responseBody);
        }
    }

    private <T> void printResponse(HttpServletResponse response, T controllerResponse) throws IOException{
        ObjectWriter writer = setDateFormatForObjectMapper().writer().withDefaultPrettyPrinter();
        String value = writer.writeValueAsString(controllerResponse);
        response.getWriter().println(value);
    }

    private ObjectMapper setDateFormatForObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }


}
