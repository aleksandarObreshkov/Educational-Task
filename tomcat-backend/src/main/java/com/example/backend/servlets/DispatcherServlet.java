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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        handleRequest(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        handleRequest(request,response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        handleRequest(request, response);
    }

    // TODO: manage wrong url
    private void handleRequest(HttpServletRequest request, HttpServletResponse response){
        for (Class<?> controllerClass : registry.getControllerClasses()) {
            if (canHandleRequest(controllerClass, request)) {
                try {
                    Object instantiatedController=controllerClass.getDeclaredConstructor().newInstance();
                    RequestEntity requestEntity = new RequestEntityProvider(request,instantiatedController).createRequestEntity();
                    Method methodToCall = getControllerMethodByAnnotation(instantiatedController,requestEntity);
                    if (methodToCall==null) {
                        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
                        return;
                    }
                    ResponseEntity<Object> result = invokeControllerMethod(methodToCall,instantiatedController,requestEntity);
                    response.setStatus(result.getStatus().value());
                    if (result.getBody()!=null){
                        printResponse(response, result);
                    }
                } catch (NoSuchMethodException | InstantiationException |
                        IllegalAccessException | IOException e) {
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                } catch (InvocationTargetException e) {
                    System.out.println(e.getTargetException().getMessage());
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        respondWithNotFound(response);

    }

    private boolean canHandleRequest(Class<?> controllerClass, HttpServletRequest request) {
        return request.getRequestURI().contains(getHandledPath(controllerClass));
    }

    private Method getControllerMethodByAnnotation(Object controller, RequestEntity entity){
        Method[] controllerMethods = controller.getClass().getDeclaredMethods();
        List<Method> annotatedMethods = getAnnotatedMethods(controllerMethods);

        for (Method a:annotatedMethods){
            if (a.getAnnotation(RequestMapping.class).method().equals(entity.getMethod())) {
                List<String> methodPathVariables = getMethodPathVariables(a);
                if (methodPathVariables.size()==entity.getPathVariables().size()){
                    return a;
                }
            }
        }
        return null;
    }

    private List<String> getMethodPathVariables(Method method){
        List<String> pathVariables = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter a:parameters){
            if (a.isAnnotationPresent(PathVariable.class)){
                pathVariables.add(a.getAnnotation(PathVariable.class).value());
            }
        }
        return pathVariables;

    }

    private List<Method>  getAnnotatedMethods(Method[] classMethods){
        return Stream.of(classMethods).collect(Collectors.toList());
    }

    private Object[] setUpParametersForMethodInvocation(Parameter[] methodParameters, RequestEntity entity) {
        List<Object> resultParameters = new ArrayList<>();
        for (Parameter p : methodParameters){
            if (!entity.getPathVariables().isEmpty()) {
                String actualParameter = entity.getPathVariables().get(p.getName());
                ObjectMapper mapper = new ObjectMapper();
                resultParameters.add(mapper.convertValue(actualParameter, p.getType()));
            }
            if (entity.getBody()!=null){
                resultParameters.add(entity.getBody());
            }
        }

        return resultParameters.toArray();
    }

    private ResponseEntity<Object> invokeControllerMethod(Method method, Object instantiatedObject, RequestEntity entity) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Parameter[] params = method.getParameters();
        Object[] methodParameters = setUpParametersForMethodInvocation(params,entity);

        Object result = method.invoke(instantiatedObject,methodParameters);

        if (result instanceof ResponseEntity){
            return (ResponseEntity<Object>) result;
        }
        throw new IllegalStateException("Invalid return entity type of controller method invocation.");
    }

    private ObjectMapper setDateFormatForObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    private <T> void printResponse(HttpServletResponse response, ResponseEntity<T> controllerResponse) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ObjectMapper mapper = setDateFormatForObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(),controllerResponse.getBody());
    }

    private void respondWithNotFound(HttpServletResponse response){
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    private String getHandledPath(Class<?> controllerClass) {
        return controllerClass.getAnnotation(RequestPath.class).value();
    }
}

