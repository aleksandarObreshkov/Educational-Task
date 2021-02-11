package com.example.backend.servlets;

import com.example.backend.RESTEntities.RequestEntityProvider;
import com.example.backend.RESTEntities.ResponseEntity;
import com.example.backend.annotations.*;
import com.example.backend.constants.HttpStatus;
import com.example.backend.controllers.CharacterController;
import com.example.backend.controllers.MovieController;
import com.example.backend.controllers.StarshipController;
import com.example.backend.utils.ControllerRegistry;
import com.example.backend.RESTEntities.RequestEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.util.ArrayList;
import java.util.List;
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

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request,response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (Class<?> controllerClass : registry.getControllerClasses()) {
            if (!canHandleRequest(controllerClass,request)){
                continue;
            }
            try {
                Object instantiatedController=controllerClass.getDeclaredConstructor().newInstance();
                RequestEntity requestEntity = new RequestEntityProvider(request).createRequestEntity();
                Method methodToCall = getControllerMethodByAnnotation(instantiatedController,requestEntity);
                if (methodToCall==null) {
                    response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
                    return;
                }
                ResponseEntity<Object> result = invokeControllerMethod(methodToCall,instantiatedController,requestEntity);
                response.setStatus(result.getStatus().value());
                if (result.getBody()!=null){
                    printResponse(response, result.getBody());
                }

            } catch (NoSuchMethodException | InstantiationException |
                    IllegalAccessException | IOException |
                    NoSuchFieldException | NullPointerException |
                    InvocationTargetException e) {
                response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                printResponse(response, e.getMessage());
            } catch (IllegalArgumentException e){
                response.setStatus(HttpStatus.BAD_REQUEST.value());
                printResponse(response, e.getMessage());
            }
            return;
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    private boolean canHandleRequest(Class<?> controllerClass, HttpServletRequest request) {
        return request.getRequestURI().contains(getHandledPath(controllerClass));
    }

    private String getHandledPath(Class<?> controllerClass) {
        return controllerClass.getAnnotation(RequestPath.class).value();
    }

    private Method getControllerMethodByAnnotation(Object controller, RequestEntity entity){
        Method[] controllerMethods = controller.getClass().getDeclaredMethods();
        List<Method> annotatedMethods = getAnnotatedMethods(controllerMethods, RequestMapping.class);
        String controllerURI = controller.getClass().getAnnotation(RequestPath.class).value();

        for (Method method:annotatedMethods){
            if (method.getAnnotation(RequestMapping.class).method().equals(entity.getMethod())) {
                if (requestMatchesMethod(controllerURI,method,entity)){
                    return method;
                }
            }
        }
        return null;
    }

    private List<Method>  getAnnotatedMethods(Method[] classMethods, Class<? extends Annotation> annotation){
        return Stream.of(classMethods).filter(method -> method.isAnnotationPresent(annotation)).collect(Collectors.toList());
    }

    private boolean requestMatchesMethod(String controllerUri, Method controllerMethod, RequestEntity entity){
        String methodUri = controllerMethod.getAnnotation(RequestMapping.class).value();
        List<String> methodPathVariables = getMethodPathVariables(controllerMethod);
        return entity.getRequestURI().equals(fillRequestUriPlaceholders(controllerUri + methodUri, methodPathVariables, entity));
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

    private String fillRequestUriPlaceholders(String requestUri, List<String> methodPathVariables ,RequestEntity entity){
        for (String pathVariable : methodPathVariables){
            if (entity.getPathVariables().get(pathVariable)!=null){
                requestUri = requestUri.replace(pathVariable,entity.getPathVariables().get(pathVariable));;
            }
        }
        return requestUri;
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

