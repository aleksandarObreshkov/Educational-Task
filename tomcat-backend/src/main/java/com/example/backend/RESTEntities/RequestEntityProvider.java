package com.example.backend.RESTEntities;

import com.example.backend.constants.HttpMethod;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Character;
import model.Movie;
import model.Starship;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    public RequestEntity createRequestEntity() throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        Pattern pattern = Pattern.compile("/tomcat_backend_war_exploded/[a-zA-z]*/(?<id>[0-9]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(request.getRequestURI());
        HttpMethod requestMethod = HttpMethod.valueOf(request.getMethod());
        RequestEntity result = new RequestEntity(requestMethod);
        if (matcher.find()){
            long id = Long.parseLong(matcher.group("id"));
            result.setId(id);
        }
        if (requestMethod.equals(HttpMethod.POST)||requestMethod.equals(HttpMethod.PUT)) {
            String requestBody = getRequestBody();
            if (!requestBody.isEmpty()) {
                result.setBody(parseRequestBody(requestBody, getEntityClassFromControllerMethod()));
            }
        }
        result.setUri(removeTrailingSlash(request.getRequestURI()));
        return result;
    }

    private String getRequestBody() throws IOException {
        BufferedReader requestBodyReader = request.getReader();
        return requestBodyReader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private Object parseRequestBody(String requestBodyString, Class<?> entityClass) throws IOException {
        return new ObjectMapper().readValue(requestBodyString,entityClass);
    }

    private String removeTrailingSlash(String uri){
        if (uri.endsWith("/")){
            return uri.substring(0,uri.length()-1);
        }
        else return uri;
    }

    private Class<?> getEntityClassFromControllerMethod() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> returnType = instantiatedController.getClass().getDeclaredMethod("post").getReturnType();
        Object bodyOfReturnType = returnType.getDeclaredMethod("getBody").invoke(returnType);
        if (bodyOfReturnType instanceof Character) return Character.class;
        else if (bodyOfReturnType instanceof Movie) return Movie.class;
        else return Starship.class;
    }

    //TODO: Make ResponseEntity capable of holding more than one PathVariable (i.e "id").

}
