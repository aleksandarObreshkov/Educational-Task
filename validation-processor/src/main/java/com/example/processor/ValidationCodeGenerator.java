package com.example.processor;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;

import javax.validation.constraints.*;
import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ValidationCodeGenerator {

    private final List<TypeName> fieldTypes;
    private final Class<? extends Annotation> annotationClass;

    public ValidationCodeGenerator(List<TypeName> fieldTypes, Class<? extends Annotation> annotationClass) {
        this.fieldTypes = fieldTypes;
        this.annotationClass = annotationClass;
    }

    public List<CodeBlock> getValidationMethodCode(){
        List<CodeBlock> parsingBlocks = new ArrayList<>();
        if (annotationClass.equals(NotNull.class)){
            parsingBlocks.add(validateNotNullAnnotation());
            return parsingBlocks;
        }
        for (TypeName type : fieldTypes){
            if (type.equals(ClassName.get(Float.class))){
                parsingBlocks.add(getFloatParsing());
            }
            else if (type.equals(ClassName.get(Double.class))){
                parsingBlocks.add(getDoubleParsing());
            }
            else if (type.equals(ClassName.get(Integer.class))){
                parsingBlocks.add(getIntegerParsing());
            }
            else if (type.equals(ClassName.get(LocalDate.class))){
                parsingBlocks.add(getLocalDateParsing());
            }
        }
        return parsingBlocks;
    }

    private CodeBlock getFloatParsing(){
        return CodeBlock
                .builder()
                .beginControlFlow("if(object.getClass().equals(Float.class))")
                .addStatement("Float item = Float.parseFloat(object.toString())")
                .addStatement("Float value = 0f ")
                .beginControlFlow("if(annotationValue!=null)")
                .addStatement("value = Float.parseFloat(annotationValue.toString())")
                .endControlFlow()
                .add(getValidationStatementForAnnotation(annotationClass))
                .endControlFlow()
                .build();
    }

    private CodeBlock getIntegerParsing(){
        return CodeBlock
                .builder()
                .beginControlFlow("if(object.getClass().equals(Integer.class))")
                .addStatement("Integer item = Integer.parseInt(object.toString())")
                .addStatement("Integer value = 0")
                .beginControlFlow("if(annotationValue!=null)")
                .addStatement("value = Integer.parseInt(annotationValue.toString())")
                .endControlFlow()
                .add(getValidationStatementForAnnotation(annotationClass))
                .endControlFlow()
                .build();
    }

    private CodeBlock getDoubleParsing(){
        return CodeBlock
                .builder()
                .beginControlFlow("if(object.getClass().equals(Double.class))")
                .addStatement("Double item = Double.parseDouble(object.toString())")
                .addStatement("Double value = 0")
                .beginControlFlow("if(annotationValue!=null)")
                .addStatement("value = Double.parseDouble(annotationValue.toString())")
                .endControlFlow()
                .add(getValidationStatementForAnnotation(annotationClass))
                .endControlFlow()
                .build();
    }

    private CodeBlock getLocalDateParsing(){
        return CodeBlock
                .builder()
                .beginControlFlow("if(object.getClass().equals(LocalDate.class))")
                .addStatement("$T item = LocalDate.parse(object.toString())", LocalDate.class)
                .addStatement("LocalDate value = LocalDate.EPOCH")
                .beginControlFlow("if(annotationValue!=null)")
                .addStatement("value = LocalDate.parse(annotationValue.toString())")
                .endControlFlow()
                .add(getValidationStatementForAnnotation(annotationClass))
                .endControlFlow()
                .build();
    }

    private static CodeBlock getValidationStatementForAnnotation(Class<? extends Annotation> annotationClass){
        if (annotationClass.equals(Positive.class)){
            return validatePositiveAnnotation();
        }
        else if(annotationClass.equals(PositiveOrZero.class)){
            return validatePositiveOrZeroAnnotation();
        }
        else if(annotationClass.equals(NotNull.class)){
            return validateNotNullAnnotation();
        }
        else if(annotationClass.equals(Max.class)){
            return validateMaxAnnotation();
        }
        else if(annotationClass.equals(Past.class)){
            return validatePastAnnotation();
        }
        throw new IllegalArgumentException("Unrecognised exception type.");
    }

    private static CodeBlock validatePositiveAnnotation(){
        return CodeBlock
                .builder()
                .add(validateObjectIsNull())
                .beginControlFlow("if(item<=0)")
                .beginControlFlow("if(message.contains(\"javax.validation\"))")
                .addStatement("message = \"must be greater than 0\"")
                .endControlFlow()
                .addStatement("throw new $T(message)", IllegalArgumentException.class)
                .endControlFlow()
                .build();
    }
    private static CodeBlock validateNotNullAnnotation(){
        return CodeBlock
                .builder()
                .beginControlFlow("if(object==null)")
                .beginControlFlow("if(message.contains(\"javax.validation\"))")
                .addStatement("message = \"should not be null\"")
                .endControlFlow()
                .addStatement("throw new $T(message)", IllegalArgumentException.class)
                .endControlFlow()
                .build();
    }
    private static CodeBlock validatePositiveOrZeroAnnotation(){
        return CodeBlock
                .builder()
                .add(validateObjectIsNull())
                .beginControlFlow("if(item<0)")
                .beginControlFlow("if(message.contains(\"javax.validation\"))")
                .addStatement("message = \"must be greater or equal to 0\"")
                .endControlFlow()
                .addStatement("throw new $T(message)", IllegalArgumentException.class)
                .endControlFlow()
                .build();
    }
    //not implemented for types other than LocalDate
    private static CodeBlock validatePastAnnotation(){
        return CodeBlock
                .builder()
                .add(validateObjectIsNull())
                .beginControlFlow("if(item.isAfter(LocalDate.now()))")
                .beginControlFlow("if(message.contains(\"javax.validation\"))")
                .addStatement("message = \"must be in the past\"")
                .endControlFlow()
                .addStatement("throw new $T(message)", IllegalArgumentException.class)
                .endControlFlow()
                .build();
    }
    private static CodeBlock validateMaxAnnotation(){
        return CodeBlock
                .builder()
                .add(validateObjectIsNull())
                .beginControlFlow("if(item>value)")
                .beginControlFlow("if(message.contains(\"javax.validation\"))")
                .addStatement("message = \"must be greater than \" + value")
                .endControlFlow()
                .addStatement("throw new $T(message)", IllegalArgumentException.class)
                .endControlFlow()
                .build();
    }

    private static CodeBlock validateObjectIsNull(){
        return CodeBlock
                .builder()
                .beginControlFlow("if(object == null)")
                .addStatement("return")
                .endControlFlow()
                .build();
    }

}
