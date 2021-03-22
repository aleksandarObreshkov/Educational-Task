package com.example.backend.servlets;

import com.example.backend.rest_entities.RequestEntity;
import com.example.backend.rest_entities.RequestEntityProvider;
import com.example.backend.rest_entities.ResponseEntity;
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
import com.example.backend.utils.ControllerRegistry;
import com.example.backend.utils.RegexUtil;
import com.example.backend.utils.URLValidator;
import com.example.model.Droid;
import com.example.model.Human;
import com.example.model.Movie;
import com.example.model.Starship;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.persistence.Entity;
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

// TODO Why is the servlet itself annotated with @RequestPath?
@RequestPath
// TODO Add a serialVersionUid constant.
public class DispatcherServlet extends HttpServlet {

    private ControllerRegistry registry;

    @Override
    public void init() throws ServletException {
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
        // TODO Remove the context path from the request URI. This will allow you to also remove the
        // "/tomcat_backend_war_exploded" prefix from controller paths. Having that prefix is not good, because it could
        // change very easily based on the name artifact produced by Maven and how that artifact is deployed to Tomcat.
        // For example, in Cloud Foundry the artifact would be deployed to the root context path "/" by default. This
        // servlet will then stop working.
        String requestUri = request.getRequestURI();
        String controllerUri = getHandledPath(controllerClass);
        // TODO It's quite normal to have the following REST API, especially when there are a lot relations between the
        // different models:
        // GET /humans
        // GET /starships
        // GET /humans/{id}
        // GET /humans/{id}/starships
        // The following line of code is therefore not a good idea, because the /starships controller could match the
        // /humans/{id}/starships endpoint even though it should be routed to the /humans controller.
        return requestUri.contains(controllerUri);
    }

    private String getHandledPath(Class<?> controllerClass) {
        return controllerClass.getAnnotation(RequestPath.class).value();
    }

    private void handleRequestWithController(HttpServletRequest request, HttpServletResponse response,
                                             Class<?> controllerClass)
            throws IOException {
        try {
            Object controller = controllerClass.getDeclaredConstructor().newInstance();
            // TODO Use controllerClass instead of controller.getClass(). It's shorter.
            String controllerUri = controller.getClass().getAnnotation(RequestPath.class).value();
            Method methodToInvoke = getMethodToInvoke(controllerClass.getDeclaredMethods(), request, controllerUri);
            if (methodToInvoke == null) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }
            RequestEntity entity = buildRequestEntity(request, methodToInvoke, controllerUri);
            ResponseEntity<Object> result = invokeControllerMethod(methodToInvoke, controller, entity);
            respond(response, result.getStatus(), result.getBody());
        } catch (MethodNotAllowedException e) {
            respond(response, HttpStatus.METHOD_NOT_ALLOWED, e.getMessage());
        } catch (IllegalArgumentException | JsonMappingException e) {
            respond(response, HttpStatus.BAD_REQUEST, e.getMessage());
            // TODO Just catch Exception and map it to internal server error. This will ensure that even if you've
            // missed an exception type, the servlet will still keep working fine. Not to mention it's shorter.
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException
                | IOException | IllegalStateException e) {
            respond(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private Method getMethodToInvoke(Method[] controllerMethods, HttpServletRequest request, String controllerUri)
            throws IOException, MethodNotAllowedException {
        List<Method> requestTypeMethods = getSuitableMethods(controllerMethods, RequestMapping.class,
                request.getMethod());
        if (requestTypeMethods.isEmpty()) {
            throw new MethodNotAllowedException("Method " + request.getMethod() + " is not allowed");
        }
        for (Method method : requestTypeMethods) {
            String methodUri = method.getAnnotation(RequestMapping.class).value();
            Map<String, Class<?>> pathVariableTypesMap = getPathVariableTypes(method);
            String methodUriFromRequest = removeTrailingSlash(getMethodUri(request.getRequestURI(), controllerUri));
            if (URLValidator.isUrlValid(methodUriFromRequest, methodUri, pathVariableTypesMap)) {
                return method;
            }
        }
        return null;
    }

    // TODO A small nitpick: It's fine for this method to know about the RequestMapping annotation - after all
    // controllerMethodSupportsHttpRequestMethod also knows about it and it's in a lower level of abstraction. Because
    // of this, I'd remove the "annotation" parameter from this method and pass RequestMapping.class directly to
    // getAnnotatedMethods.
    private List<Method> getSuitableMethods(Method[] classMethods, Class<? extends Annotation> annotation,
                                            String requestMethod) {
        return getAnnotatedMethods(classMethods, annotation).stream()
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
        String methodUri = getMethodUri(request.getRequestURI(), controllerUri);
        String methodUriWithPlaceholders = methodToInvoke.getAnnotation(RequestMapping.class).value();
        List<String> methodPathVariables = getMethodPathVariables(methodToInvoke);
        return new RequestEntityProvider(request.getReader()).createRequestEntity(methodPathVariables,
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

    // TODO pathVariablePlaceholders is a misleading name. The list doesn't actually contain placeholders, but rather
    // path variable names.
    private Map<String, String> getPathVariableValues(String uriWithPathVariablePlaceholders, String uri,
                                                      List<String> pathVariablePlaceholders) {
        String regex = RegexUtil.buildPathVariableCapturingRegex(uriWithPathVariablePlaceholders);
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(uri);
        Map<String, String> uriPathVariables = new HashMap<>();
        if (!matcher.matches()) {
            throw new IllegalStateException("Method URI doesn't match method's path variable annotations.");
        }
        for (String pathVariable : pathVariablePlaceholders) {
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
                // TODO This is not good. If you were to add another type of entity, Jedi for example, you'd have to
                // remember to add it here. There are multiple ways to fix this in a more generic way. The first that
                // comes to mind is generating the validators with a class-level annotation that indicates what they're
                // validating. For example, @ValidatorFor(Movie.class). You can then scan the classpath for classes
                // annotated with this annotation and look for one that matches the type of the parameter.
                if (entityClass.isAnnotationPresent(Entity.class)) {
                    if (mappingResult instanceof Human) {
                        HumanValidator.validate(mappingResult);
                    } else if (mappingResult instanceof Droid) {
                        DroidValidator.validate(mappingResult);
                    } else if (entityClass.equals(Movie.class)) {
                        MovieValidator.validate(mappingResult);
                    } else if (entityClass.equals(Starship.class)) {
                        StarshipValidator.validate(mappingResult);
                    }
                }
                resultParameters.add(mappingResult);
            }
            if (parameter.isAnnotationPresent(PathVariable.class)) {
                String pathVariableName = parameter.getAnnotation(PathVariable.class).value();
                String actualParameter = entity.getPathVariables().get(pathVariableName);
                // TODO Why aren't you using your ValueConverter here?
                resultParameters.add(mapper.convertValue(actualParameter, parameter.getType()));
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
        ObjectWriter writer = new ObjectMapper().writer();
        String value = writer.writeValueAsString(controllerResponse);
        response.getWriter().println(value);
    }

}
