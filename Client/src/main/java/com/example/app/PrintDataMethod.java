package com.example.app;

import java.util.List;

public class PrintDataMethod {

    public static <T> void printData(List<T> data){
        for(Object a : data){
            System.out.println(a.toString());
        }
    }
}
