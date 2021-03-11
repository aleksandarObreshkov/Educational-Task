package com.example.processor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class AnnotationRegistry {
    private static AnnotationRegistry instance;
    public static AnnotationRegistry getInstance() {
        if (instance == null) {
            instance = new AnnotationRegistry();
        }
        return instance;
    }
    private final List<Class<? extends Annotation>> annotationClasses = new ArrayList<>();
    public void register(Class<? extends Annotation> controllerClass) {
        annotationClasses.add(controllerClass);
    }
    public List<Class<? extends Annotation>> getAnnotationClasses() {
        return annotationClasses;
    }
}
