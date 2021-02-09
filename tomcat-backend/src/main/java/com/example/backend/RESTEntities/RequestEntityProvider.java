package com.example.backend.RESTEntities;

import com.example.backend.annotations.RequestBody;
import com.example.backend.constants.HttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestEntityProvider {

    private final HttpServletRequest request;
    private final Object instantiatedController;

    public RequestEntityProvider(HttpServletRequest request, Object instantiatedController) {
        this.request = request;
        this.instantiatedController=instantiatedController;
    }

    public RequestEntity createRequestEntity() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, NoSuchFieldException, InstantiationException {

        Pattern pattern = Pattern.compile("/tomcat_backend_war_exploded/[a-zA-z]*/(?<id>[0-9]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request.getRequestURI());
        HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());
        RequestEntity result = new RequestEntity(requestMethod);
        if (matcher.find()){
            Map<String, String> pathVariables = new HashMap<>();
            pathVariables.put("id", matcher.group("id"));
            result.setPathVariables(pathVariables);
        }
        if (requestMethod.equals(HttpMethod.POST)||requestMethod.equals(HttpMethod.PUT)) {
            String requestBody = getRequestBody();
            if (!requestBody.isEmpty()) {
                result.setBody(parseRequestBody(requestBody, getEntityClassFromControllerMethod()));
            }
        }
        return result;
    }

    private String getRequestBody() throws IOException {
        BufferedReader requestBodyReader = request.getReader();
        return requestBodyReader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private Object parseRequestBody(String requestBodyString, Class<?> entityClass) throws IOException {
        return new ObjectMapper().readValue(requestBodyString,entityClass);
    }

    private Method takePostMethod(Method[] controllerMethods){
        for (Method m : controllerMethods){
            if (m.getName().equals("post")){
                return m;
            }
        }
        return null;
    }

    // TODO: handle the possibility of NullPointer exception
    private Class<?> getRequestBodyAnnotatedField(Method[] controllerMethods){
        Parameter[] methodParams = takePostMethod(controllerMethods).getParameters();
        for (Parameter p : methodParams){
            if (p.isAnnotationPresent(RequestBody.class)){
                return p.getType();
            }
        }
        return null;
    }

    private Class<?> getEntityClassFromControllerMethod() {
        Method[] controllerMethods = instantiatedController.getClass().getMethods();
        return getRequestBodyAnnotatedField(controllerMethods);
    }

}
