package com.example.servlets;

import com.example.annotations.*;
import com.example.processor.ValidatorFor;
import com.example.rest_entities.RequestEntity;
import com.example.rest_entities.RequestEntityProvider;
import com.example.rest_entities.ResponseEntity;
import com.example.constants.HttpMethod;
import com.example.constants.HttpStatus;
import com.example.controllers.CharacterController;
import com.example.controllers.MovieController;
import com.example.controllers.StarshipController;
import com.example.errors.MethodNotAllowedException;
import com.example.utils.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.reflections.Reflections;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DispatcherServlet extends HttpServlet {

    private static final Long serialVersionUid = 1L;

    private ControllerRegistry registry;

    @Override
    public void init() {
        registry = ControllerRegistry.getInstance();
        registry.register(MovieController.class);
        registry.register(CharacterController.class);
        registry.register(StarshipController.class);
    }

    public void registerController(Class<?> controllerClass) {
        registry.register(controllerClass);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        for (Class<?> controllerClass : registry.getControllerClasses()) {
            if (canHandleRequest(controllerClass, request)) {
                handleRequestWithController(request, response, controllerClass);
                return;
            }
        }
        response.setStatus(HttpStatus.NOT_FOUND.value());
    }

    private boolean canHandleRequest(Class<?> controllerClass, HttpServletRequest request) {
        String requestUri = removeContextPathFromRequest(request.getRequestURI(), request.getContextPath());
        String controllerUri = getHandledPath(controllerClass);
        return requestUri.startsWith(controllerUri);
    }

    private String removeContextPathFromRequest(String requestUri, String contextPath){
        return requestUri.replace(contextPath, "");
    }

    private String getHandledPath(Class<?> controllerClass) {
        return controllerClass.getAnnotation(RequestPath.class).value();
    }

    private void handleRequestWithController(HttpServletRequest request, HttpServletResponse response,
                                             Class<?> controllerClass)
            throws IOException {
        try {
            Object controller = controllerClass.getDeclaredConstructor().newInstance();
            String controllerUri = controllerClass.getAnnotation(RequestPath.class).value();
            Method methodToInvoke = getMethodToInvoke(controllerClass.getMethods(), request, controllerUri);
            if (methodToInvoke == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }
            RequestEntity entity = buildRequestEntity(request, methodToInvoke, controllerUri);
            ResponseEntity<Object> result = invokeControllerMethod(methodToInvoke, controller, entity);
            respond(response, result.getStatus(), result.getBody());
        } catch (MethodNotAllowedException e) {
            respond(response, HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
            //Added InvocationTargetException, because this is the wrapping exception for an exception,
            //thrown by the method, called by .invoke()
            //getCause() is called, because the InvocationTargetException doesn't get the message from the
            //inner exception
        } catch (InvocationTargetException e){
            respond(response, HttpStatus.BAD_REQUEST, e.getCause().getMessage());
        } catch (IllegalArgumentException | JsonMappingException e) {
            respond(response, HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            respond(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private Method getMethodToInvoke(Method[] controllerMethods, HttpServletRequest request, String controllerUri)
            throws MethodNotAllowedException {
        List<Method> requestTypeMethods = getSuitableMethods(controllerMethods, request.getMethod());
        if (requestTypeMethods.isEmpty()) {
            throw new MethodNotAllowedException("Method " + request.getMethod() + " is not allowed");
        }
        for (Method method : requestTypeMethods) {
            String methodUri = method.getAnnotation(RequestMapping.class).value();
            Map<String, Class<?>> pathVariableTypesMap = getPathVariableTypes(method);
            String requestUri = removeContextPathFromRequest(request.getRequestURI(), request.getContextPath());
            String methodUriFromRequest = removeTrailingSlash(getMethodUri(requestUri, controllerUri));
            if (URLParser.parseUrl(methodUriFromRequest, methodUri, pathVariableTypesMap)) {
                return method;
            }
        }
        return null;
    }

    private List<Method> getSuitableMethods(Method[] classMethods, String requestMethod) {
        return getAnnotatedMethods(classMethods, RequestMapping.class).stream()
                .filter(method -> controllerMethodSupportsHttpRequestMethod(method, requestMethod))
                .collect(Collectors.toList());
    }

    private List<Method> getAnnotatedMethods(Method[] classMethods, Class<? extends Annotation> annotation) {
        return Stream.of(classMethods)
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }

    private boolean controllerMethodSupportsHttpRequestMethod(Method method, String httpMethodFromRequest) {
        HttpMethod handleableHttpMethod = method.getAnnotation(RequestMapping.class).method();
        HttpMethod requestHttpMethod = HttpMethod.valueOf(httpMethodFromRequest);
        return handleableHttpMethod.equals(requestHttpMethod);
    }

    private Map<String, Class<?>> getPathVariableTypes(Method method) {
        Map<String, Class<?>> result = new HashMap<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(PathVariable.class)) {
                result.put(parameter.getAnnotation(PathVariable.class).value(), parameter.getType());
            }
        }
        return result;
    }

    private String removeTrailingSlash(String url) {
        if (url.endsWith("/")) {
            return url.substring(0, url.length() - 1);
        }
        return url;
    }

    private RequestEntity buildRequestEntity(HttpServletRequest request, Method methodToInvoke, String controllerUri)
            throws IOException {
        String requestUri = removeContextPathFromRequest(request.getRequestURI(), request.getContextPath());
        requestUri = removeTrailingSlash(requestUri);
        String methodUri = getMethodUri(requestUri, controllerUri);
        String methodUriWithPlaceholders = methodToInvoke.getAnnotation(RequestMapping.class).value();
        List<String> methodPathVariables = getMethodPathVariables(methodToInvoke);
        return new RequestEntityProvider().createRequestEntity(request.getReader(),
                getPathVariableValues(methodUriWithPlaceholders, methodUri, methodPathVariables));
    }

    private String getMethodUri(String requestUri, String controllerUri) {
        if (requestUri != null && !requestUri.isEmpty()) {
            return requestUri.replace(controllerUri, "");
        }
        throw new IllegalArgumentException("Request URI was null");

    }

    private List<String> getMethodPathVariables(Method method) {
        List<String> pathVariables = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            if (parameter.isAnnotationPresent(PathVariable.class)) {
                pathVariables.add(parameter.getAnnotation(PathVariable.class).value());
            }
        }
        return pathVariables;
    }

    private Map<String, String> getPathVariableValues(String uriWithPathVariablePlaceholders, String uri,
                                                      List<String> pathVariableNames) {
        String regex = RegexUtil.buildPathVariableCapturingRegex(uriWithPathVariablePlaceholders);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);
        Map<String, String> uriPathVariables = new HashMap<>();
        if (!matcher.matches()) {
            throw new IllegalStateException("Method URI doesn't match method's path variable annotations.");
        }
        for (String pathVariable : pathVariableNames) {
            uriPathVariables.put(pathVariable, matcher.group(pathVariable));
        }
        return uriPathVariables;
    }

    private ResponseEntity<Object> invokeControllerMethod(Method method, Object instantiatedObject,
                                                          RequestEntity entity)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, JsonProcessingException {
        Parameter[] parameters = method.getParameters();
        Object[] argumentsForMethodInvocation = setUpParametersForMethodInvocation(parameters, entity);
        Object result = method.invoke(instantiatedObject, argumentsForMethodInvocation);
        if (result instanceof ResponseEntity) {
            return (ResponseEntity<Object>) result;
        }
        throw new IllegalStateException("Invalid return entity type of controller method invocation: "+result.getClass());
    }

    private Object[] setUpParametersForMethodInvocation(Parameter[] methodParameters, RequestEntity entity)
            throws JsonProcessingException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        List<Object> resultParameters = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        for (Parameter parameter : methodParameters) {
            if (parameter.isAnnotationPresent(RequestBody.class)) {
                Class<?> entityClass = parameter.getType();
                Object mappingResult = mapper.readValue(entity.getBody(), entityClass);
                Reflections reflections = new Reflections("com.example");
                Set<Class<?>> validatorClasses = reflections.getTypesAnnotatedWith(ValidatorFor.class);
                for (Class<?> validator : validatorClasses){
                    if (validator.getAnnotation(ValidatorFor.class).value().equals(mappingResult.getClass())){
                        validator.getMethod("validate", Object.class).invoke(validator, mappingResult);
                    }
                }
                resultParameters.add(mappingResult);
            }
            if (parameter.isAnnotationPresent(PathVariable.class)) {
                String pathVariableName = parameter.getAnnotation(PathVariable.class).value();
                String actualParameter = entity.getPathVariables().get(pathVariableName);
                resultParameters.add(VariableConverter.convert(actualParameter,parameter.getType()));
            }
        }

        return resultParameters.toArray();
    }

    private void respond(HttpServletResponse response, HttpStatus status, Object responseBody) throws IOException {
        response.setStatus(status.value());
        response.setContentType("application/json");
        if (responseBody != null) {
            printResponse(response, responseBody);
        }
    }

    private <T> void printResponse(HttpServletResponse response, T controllerResponse) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String responseString;
        if (controllerResponse instanceof List){
           Class<?> entityClass =  ((List<Object>) controllerResponse).get(0).getClass();
           CollectionType type = mapper.getTypeFactory().constructCollectionType(List.class, entityClass);
           responseString = mapper.writerFor(type).writeValueAsString(controllerResponse);
        }
        else {
            responseString = mapper.writeValueAsString(controllerResponse);
        }
        response.getWriter().println(responseString);
    }

}
