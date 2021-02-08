package com.example.backend.servlets;

import com.example.backend.RESTEntities.RequestEntityProvider;
import com.example.backend.RESTEntities.ResponseEntity;
import com.example.backend.annotations.*;
import com.example.backend.constants.HttpMethod;
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
import java.util.ArrayList;
import java.util.List;

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

    private void handleRequest(HttpServletRequest request, HttpServletResponse response){
        for (Class<?> controllerClass : registry.getControllerClasses()) {
            if (canHandleRequest(controllerClass, request)) {
                try {
                    Object instantiatedController=controllerClass.getDeclaredConstructor().newInstance();
                    RequestEntity requestEntity = new RequestEntityProvider(request,instantiatedController).createRequestEntity();
                    Method methodToCall = getProperControllerMethod(instantiatedController,requestEntity);
                    if (methodToCall==null) {
                        response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
                        return;
                    }
                    ResponseEntity<Object> result = invokeControllerMethod(methodToCall,instantiatedController,requestEntity);
                    response.setStatus(result.getStatus().value());
                    printResponse(response, result);
                } catch (NoSuchMethodException | InstantiationException |
                        IllegalAccessException | IOException e) {
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                } catch (InvocationTargetException e) {
                    System.out.println(e.getTargetException().getMessage());
                }
                return;
            }
        }
        respondWithNotFound(response);

    }

    private boolean canHandleRequest(Class<?> controllerClass, HttpServletRequest request) {
        return request.getRequestURI().contains(getHandledPath(controllerClass));
    }

    private Method getProperControllerMethod(Object controller, RequestEntity entity){
        return getControllerMethodByAnnotation(controller,entity);
    }

    private Method getControllerMethodByAnnotation(Object controller, RequestEntity entity){
        Method[] controllerMethods = controller.getClass().getDeclaredMethods();
        final String wrapperURI = controller.getClass().getAnnotation(RequestPath.class).value();
        List<Method> annotatedMethods = getAnnotatedMethods(controllerMethods);
        for (Method a:annotatedMethods){
            if (a.getAnnotation(RequestMapping.class).method().equals(entity.getMethod())) {
                String annotationURI = wrapperURI + a.getAnnotation(RequestMapping.class).value();
                if (entity.getUri().equals(addIdToAnnotationURI(annotationURI,entity.getId()))) {
                    return a;
                }
            }
        }
        return null;
    }

    private List<Method>  getAnnotatedMethods(Method[] classMethods){
        List<Method> result = new ArrayList<>();
        for (Method a:classMethods){
            if (a.getAnnotation(RequestMapping.class)!=null){
                result.add(a);
            }
        }
        return result;

    }

    private String addIdToAnnotationURI(String uri, long id){
        return uri.replace("{id}",id+"");
    }

    private ResponseEntity<Object> invokeControllerMethod(Method method, Object instantiatedObject, RequestEntity entity) throws InvocationTargetException, IllegalAccessException {
        Object result=null;

        if (entity.getMethod().equals(HttpMethod.GET)&&entity.getId()!=0L){
            result = method.invoke(instantiatedObject,entity.getId());
        }
        else if (entity.getMethod().equals(HttpMethod.GET)&&entity.getId()==0L){
            result = method.invoke(instantiatedObject);
        }
        else if (entity.getMethod().equals(HttpMethod.DELETE)){
            result = method.invoke(instantiatedObject, entity.getId());
        }
        else if (entity.getMethod().equals(HttpMethod.POST)){
            result = method.invoke(instantiatedObject,entity.getBody());
        }

        if (result instanceof ResponseEntity){
            return (ResponseEntity) result;
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

