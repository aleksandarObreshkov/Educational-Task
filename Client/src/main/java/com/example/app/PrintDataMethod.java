package com.example.app;

import java.util.List;

//this is not a good name
//this class is redundant
public class PrintDataMethod {

    public static <T> void printData(List<T> data){
        for(Object a : data){
            System.out.println(a.toString());
        }
    }
}
