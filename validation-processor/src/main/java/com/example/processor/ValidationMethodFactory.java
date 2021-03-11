package com.example.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;
import java.lang.annotation.Annotation;
import java.util.List;

public class ValidationMethodFactory {

    public static MethodSpec createValidationMethod(Class<? extends Annotation> annotationClass, List<TypeName> fieldClasses){
        ValidationCodeGenerator generator = new ValidationCodeGenerator(fieldClasses, annotationClass);
        List<CodeBlock> typeConversionStatements = generator.getValidationMethodCode();
         MethodSpec.Builder methodSpec= MethodSpec
                .methodBuilder("validate"+annotationClass.getSimpleName())
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(ClassName.VOID)
                .addParameter(ClassName.get(Object.class), "object")
                .addParameter(ClassName.get(String.class), "message")
                 .addParameter(ClassName.get(Object.class), "annotationValue");

        for (CodeBlock typeConversionBlock : typeConversionStatements){
            methodSpec.addCode(typeConversionBlock);
        }
        return methodSpec.build();
    }
}
