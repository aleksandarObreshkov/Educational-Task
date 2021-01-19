package com.example.backend.servlets;

import com.example.backend.annotations.DeleteMapping;
import com.example.backend.annotations.GetMapping;
import com.example.backend.annotations.PostMapping;
import com.example.backend.annotations.RequestPath;
import com.example.backend.constants.HttpStatus;
import com.example.backend.controllers.CharacterController;
import com.example.backend.controllers.MovieController;
import com.example.backend.controllers.StarshipController;
import com.example.backend.errors.ErrorEntity;
import com.example.backend.utils.ControllerRegistry;
import com.example.backend.RESTEntities.RequestEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Character;
import model.Movie;
import model.Starship;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequestPath
public class DispatcherServlet extends HttpServlet {
    private ControllerRegistry registry;
    private boolean requestHandled;

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
                    RequestEntity requestEntity = setupRequestEntity(request);
                    Method methodToCall = getProperControllerMethod(instantiatedController,requestEntity);
                    Object result = invokeControllerMethod(methodToCall,instantiatedController,requestEntity);
                    response.setStatus(getResponseStatus(result).value());
                    printResponse(response, result);
                    requestHandled=true;
                    break;
                } catch (NoSuchMethodException | InstantiationException |
                        IllegalAccessException | IOException e) {
                    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
                } catch (InvocationTargetException e) {
                    System.out.println(e.getTargetException().getMessage());
                }
            }
        }
        if (!requestHandled){
            respondWithNotFound(response);
        }
    }

    private boolean canHandleRequest(Class<?> controllerClass, HttpServletRequest request) {
        return request.getRequestURI().contains(controllerClass.getAnnotation(RequestPath.class).value());
    }

    private void respondWithNotFound(HttpServletResponse response){
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    private RequestEntity setupRequestEntity(HttpServletRequest request) throws IOException {

        Pattern pattern = Pattern.compile("/tomcat_war/[a-zA-z]*/(?<id>[0-9]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request.getRequestURI());
        String requestMethod = request.getMethod().toLowerCase();
        RequestEntity result = new RequestEntity(requestMethod);
        if (matcher.find()){
            long id = Long.parseLong(matcher.group("id"));
            result.setId(id);
        }
        if (requestMethod.equals("post")||requestMethod.equals("put")) {
            String requestBody = getRequestBody(request);
            if (!requestBody.isEmpty()) {
                result.setBody(parseRequestBody(requestBody, getEntityClassFromURI(request.getRequestURI())));
            }
        }
        result.setUri(fixRequestURI(request.getRequestURI()));
        return result;
    }

    private HttpStatus getResponseStatus(Object controllerResponse) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (HttpStatus) controllerResponse.getClass().getDeclaredMethod("getStatus").invoke(controllerResponse);
    }

    private void printResponse(HttpServletResponse response, Object requestResult) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ObjectMapper mapper = setDateFormatForObjectMapper();
        Object result = requestResult.getClass().getDeclaredMethod("getBody").invoke(requestResult);
        mapper.writerWithDefaultPrettyPrinter().writeValue(response.getOutputStream(),result);
    }

    private String getRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader requestBodyReader = request.getReader();
        return requestBodyReader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private Object parseRequestBody(String requestBodyString, Class<?> entityClass) throws IOException {
        return new ObjectMapper().readValue(requestBodyString,entityClass);
    }

    private Class<?> getEntityClassFromURI(String uri){
        if (uri.contains("characters")) return Character.class;
        else if (uri.contains("movies")) return Movie.class;
        else return Starship.class;
    }

    private ObjectMapper setDateFormatForObjectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }

    private Method getProperControllerMethod(Object controller, RequestEntity entity){
        Method[] controllerMethods = controller.getClass().getDeclaredMethods();
        final String wrapperURI = controller.getClass().getAnnotation(RequestPath.class).value();

        switch (entity.getMethod()){
            case "post":{
                List<Method> annotatedMethods = getAnnotatedMethods(controllerMethods,PostMapping.class);
                for (Method a:annotatedMethods){
                    String annotationURI = wrapperURI + a.getAnnotation(PostMapping.class).value();
                    if (entity.getUri().equals(annotationURI)){
                        return a;
                    }
                }
            }
            case "delete":{
                List<Method> annotatedMethods = getAnnotatedMethods(controllerMethods,DeleteMapping.class);
                for (Method a:annotatedMethods){
                    String annotationURI = wrapperURI + a.getAnnotation(DeleteMapping.class).value();
                    if (entity.getUri().equals(addIdToAnnotationURI(annotationURI,entity.getId()))){
                        return a;
                    }
                }
            }
            case "get":{
                List<Method> annotatedMethods = getAnnotatedMethods(controllerMethods,GetMapping.class);
                for (Method a:annotatedMethods){
                    String annotationURI = wrapperURI + a.getAnnotation(GetMapping.class).value();
                    if (entity.getUri().equals(addIdToAnnotationURI(annotationURI,entity.getId()))){
                        return a;
                    }
                }
            }
            default: {
                return null;
            }
        }

    }

    private <T extends Annotation> List<Method>  getAnnotatedMethods(Method[] classMethods, Class<T> annotation){
        List<Method> result = new ArrayList<>();
        for (Method a:classMethods){
            if (a.getAnnotation(annotation)!=null){
                result.add(a);
            }
        }
        return result;

    }

    private String addIdToAnnotationURI(String uri, long id){
        return uri.replace("{id}",id+"");
    }

    // TODO: Rename properly
    private String fixRequestURI(String uri){
        if (uri.endsWith("/")){
            return uri.substring(0,uri.length()-1);
        }
        else return uri;
    }

    private Object invokeControllerMethod(Method method, Object instantiatedObject, RequestEntity entity) throws InvocationTargetException, IllegalAccessException {
        Object result=null;
        try{
            if (entity.getMethod().equals("get")&&entity.getId()!=0L){
                result = method.invoke(instantiatedObject,entity.getId());
            }
            else if (entity.getMethod().equals("get")&&entity.getId()==0L){
                result = method.invoke(instantiatedObject);
            }
            else if (entity.getMethod().equals("delete")){
                result = method.invoke(instantiatedObject, entity.getId());
            }
            else if (entity.getMethod().equals("post")){
                result = method.invoke(instantiatedObject,entity.getBody());
            }
            return result;
        }catch (NullPointerException npe){
            return new ErrorEntity("No compatible method to handle request. ",HttpStatus.UNSUPPORTED_MEDIA_TYPE,npe);
        }

    }
}

