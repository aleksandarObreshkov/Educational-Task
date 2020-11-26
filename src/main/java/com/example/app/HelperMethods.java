package com.example.app;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


import java.io.File;
import java.io.IOException;
import java.util.List;

public class HelperMethods {

    public static void writeDataToFile(Object data, File file) throws IOException {
        ObjectMapper mapper=new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
    }

    public static <T> List<T> getDataFromFile(File file, Class<T> target) throws IOException{
        ObjectMapper mapper = new ObjectMapper();
        JavaType newType = mapper.getTypeFactory().constructCollectionType(List.class,target);
        return mapper.readValue(file,newType);
    }

    public static <T> void printData(List<T> data){
        for(Object a : data){
            System.out.println(a.toString());
        }
    }


}
