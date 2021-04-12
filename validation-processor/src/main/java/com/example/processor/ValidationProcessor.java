package com.example.processor;

import com.squareup.javapoet.*;
import jdk.dynalink.linker.support.TypeUtilities;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.validation.constraints.*;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ValidationProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private AnnotationRegistry annotationRegistry;
    private Types typeUtilities;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        annotationRegistry = fillAnnotationRegistry();
        this.typeUtilities=processingEnv.getTypeUtils();
    }

    private AnnotationRegistry fillAnnotationRegistry() {
        AnnotationRegistry registry = AnnotationRegistry.getInstance();
        registry.register(Positive.class);
        registry.register(PositiveOrZero.class);
        registry.register(NotNull.class);
        registry.register(Past.class);
        registry.register(Max.class);
        return registry;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new HashSet<>();
        annotations.add(Validate.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element entityClass : roundEnv.getElementsAnnotatedWith(Validate.class)) {
            if (!entityClass.getKind().equals(ElementKind.CLASS)) {
                error(entityClass, "Only classes can be annotated as entities.");
                return true;
            }
           buildValidationClass(entityClass);
        }
        return true;
    }

    private void buildValidationClass(Element entityClass){
        List<CodeBlock> ifBlocks = new ArrayList<>();
        List<MethodSpec> validationMethods = new ArrayList<>();

        String className = entityClass.getSimpleName() + "Validator";

        Map<Class<? extends Annotation>, List<TypeName>> fieldsWithAnnotation = getAnnotatedFields(
                entityClass);
        fieldsWithAnnotation = iterateOverSuperClassElements(entityClass, fieldsWithAnnotation);

        for (Class<? extends Annotation> annotation : fieldsWithAnnotation.keySet()) {
            MethodSpec method = ValidationMethodFactory.createValidationMethod(annotation,
                    fieldsWithAnnotation.get(annotation));
            validationMethods.add(method);
            ifBlocks.add(ifStatementForAnnotationType(annotation));
        }
        buildClass(className, validationMethods, ifBlocks, entityClass.asType());
    }

    private Map<Class<? extends Annotation>, List<TypeName>> getAnnotatedFields(Element entityClass) {
        List<? extends Element> classElements = entityClass.getEnclosedElements();
        Map<Class<? extends Annotation>, List<TypeName>> fieldsWithAnnotation = new HashMap<>();
        for (Element classElement : classElements) {
            if (classElement.getKind() != ElementKind.FIELD) {
                continue;
            }

            // fill the map with Annotation - <fields types, that have the annotation>
            for (Class<? extends Annotation> annotation : annotationRegistry.getAnnotationClasses()) {
                if (classElement.getAnnotation(annotation) == null) {
                    continue;
                }
                TypeName fieldType = ClassName.get(classElement.asType());
                fieldsWithAnnotation.computeIfAbsent(annotation, a -> new ArrayList<>()).add(fieldType);
            }
        }
        return fieldsWithAnnotation;
    }

    private Map<Class<? extends Annotation>, List<TypeName>> iterateOverSuperClassElements(
            Element subClass,
            Map<Class<? extends Annotation>, List<TypeName>> subclassAnnotations) {

        TypeElement classAsTypeElement = (TypeElement) subClass;
        TypeMirror superclass = classAsTypeElement.getSuperclass();
        if (superclass.getKind().equals(TypeKind.NONE)) {
            return subclassAnnotations;
        }
        Element superClass = typeUtilities.asElement(superclass);
        subclassAnnotations = mergeAnnotationMaps(getAnnotatedFields(superClass), subclassAnnotations);
        return iterateOverSuperClassElements(superClass,subclassAnnotations);
    }

    private Map<Class<? extends Annotation>, List<TypeName>> mergeAnnotationMaps(
            Map<Class<? extends Annotation>, List<TypeName>> map1,
            Map<Class<? extends Annotation>, List<TypeName>> map2){

        return Stream.of(map1, map2)
                .flatMap(map -> map.entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (map1List, map2List)->{
                            for (TypeName name : map2List){
                                if (!map1List.contains(name)){
                                    map1List.add(name);
                                }
                            }
                            return map1List;
                        }));
    }

    private String createValidationMethodCall(Class<? extends Annotation> annotationClass) {
        return "validate" + annotationClass.getSimpleName() + "(object, message, annotationValue)";
    }

    private CodeBlock ifStatementForAnnotationType(Class<? extends Annotation> annotationClass) {
        return CodeBlock.builder()
                .beginControlFlow("if(annotation.annotationType().equals($T.class))", annotationClass)
                .addStatement(createValidationMethodCall(annotationClass))
                .endControlFlow()
                .build();
    }

    private CodeBlock accessPrivateFieldsBlock() {
        // get the fields from super classes
        return CodeBlock.builder()
                .addStatement("$T[] fieldsArray = item.getClass().getDeclaredFields()", Field.class)
                .addStatement("$T<$T> fields = new $T<>()", List.class, Field.class, ArrayList.class)
                .addStatement("fields.addAll($T.asList(fieldsArray))", Arrays.class)
                .addStatement("fields = superclassFieldCollector(item.getClass(), fields)")
                .addStatement("$T<$T, $T<$T>> fieldAnnotationsMap = new $T<>()", Map.class, Field.class, List.class,
                        Annotation.class, HashMap.class)
                .beginControlFlow("for ($T field : fields)", Field.class)
                .addStatement("field.setAccessible(true)")
                .beginControlFlow("if (field.getAnnotations().length>0)")
                .addStatement("List<Annotation> annotations = filterSupportedAnnotations(field)")
                .addStatement("fieldAnnotationsMap.put(field, annotations)")
                .endControlFlow()
                .endControlFlow()
                .build();
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    private MethodSpec buildSuperclassFieldCollectorMethod(){
        return MethodSpec
                .methodBuilder("superclassFieldCollector")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(List.class, Field.class))
                .addParameter(ClassName.get(Class.class), "itemClass")
                .addParameter(ParameterizedTypeName.get(List.class, Field.class), "classFields")
                .beginControlFlow("if(itemClass.getSuperclass().equals($T.class))", Object.class)
                .addStatement("return classFields")
                .endControlFlow()
                .addStatement("$T[] currentClassFields = itemClass.getSuperclass().getDeclaredFields()", Field.class)
                .beginControlFlow("for($T field : currentClassFields)", Field.class)
                .beginControlFlow("if(!classFields.contains(field))")
                .addStatement("classFields.add(field)")
                .endControlFlow()
                .endControlFlow()
                .addStatement("return superclassFieldCollector(itemClass.getSuperclass(), classFields)")
                .build();

    }

    private MethodSpec buildFilterSupportedAnnotations(){
        return MethodSpec.methodBuilder("filterSupportedAnnotations")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .addParameter(ClassName.get(Field.class), "field")
                .returns(ParameterizedTypeName.get(List.class, Annotation.class))
                .addStatement("return $T.stream(field.getAnnotations())"
                        + ".filter(annotation -> "
                        + "annotation.annotationType().getPackage().getName().startsWith(\"javax.validation.constraints\")"
                        + ").collect($T.toList())", Arrays.class, Collectors.class)
                .build();
    }

    private MethodSpec buildSingleFieldValidationMethod(List<CodeBlock> ifStatements) {
        MethodSpec.Builder methodSpec = MethodSpec.methodBuilder("validateField")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addException(IllegalAccessException.class)
                .returns(ClassName.VOID)
                .addException(NoSuchMethodException.class)
                .addException(InvocationTargetException.class)
                .addParameter(ClassName.get(Object.class), "object")
                .addParameter(ParameterizedTypeName.get(List.class, Annotation.class), "annotations")
                .beginControlFlow("if(annotations.contains($T.class))", NotNull.class)
                .addStatement("$T notNullAnnotation = annotations.get(annotations.indexOf($T.class))", Annotation.class, NotNull.class)
                .addStatement("String message = notNullAnnotation.annotationType().getMethod(\"message\").invoke(notNullAnnotation).toString()")
                .addStatement("$T annotationValue = null;", Object.class)
                .beginControlFlow("try")
                .addStatement("annotationValue = notNullAnnotation.annotationType().getMethod(\"value\").invoke(notNullAnnotation)")
                .endControlFlow()
                .beginControlFlow("catch($T e)", NoSuchMethodException.class)
                .addStatement("System.out.println(\"Annotation doesn't have value() method.\");")
                .endControlFlow()
                .addStatement("validateNotNull(object, message, annotationValue)")
                .addStatement("annotations.remove(notNullAnnotation)")
                .endControlFlow()
                .beginControlFlow("for (Annotation annotation : annotations)")
                .addStatement(
                        "String message = annotation.annotationType().getMethod(\"message\").invoke(annotation).toString()")
                .addStatement("Object annotationValue = null")
                .beginControlFlow("try")
                .addStatement("annotationValue = annotation.annotationType().getMethod(\"value\").invoke(annotation)")
                .endControlFlow()
                .beginControlFlow("catch($T e)", NoSuchMethodException.class)
                .addStatement("System.out.println(\"Annotation doesn't have value() method.\")")
                .endControlFlow();

        for (CodeBlock block : ifStatements) {
            methodSpec.addCode(block);
        }
        methodSpec.endControlFlow();

        return methodSpec.build();
    }

    private MethodSpec buildMainValidationMethod() {
        return MethodSpec.methodBuilder("validate")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addException(IllegalAccessException.class)
                .addException(NoSuchMethodException.class)
                .addException(InvocationTargetException.class)
                .returns(ClassName.VOID)
                .addParameter(ClassName.get(Object.class), "item")
                .addCode(accessPrivateFieldsBlock())
                .beginControlFlow("for (Field object : fieldAnnotationsMap.keySet())")
                .addStatement("validateField(object.get(item), fieldAnnotationsMap.get(object))")
                .endControlFlow()
                .build();
    }

    private void buildClass(String className, List<MethodSpec> validationMethods,
                            List<CodeBlock> ifBlocksForAnnotationType, TypeMirror classToValidate) {
        AnnotationSpec a = AnnotationSpec
                .builder(ValidatorFor.class)
                .addMember("value", "$T.class", classToValidate)
                .build();
        TypeSpec.Builder classToGenerate = TypeSpec
                .classBuilder(className)
                .addAnnotation(a)
                .addModifiers(Modifier.PUBLIC);

        classToGenerate.addMethods(validationMethods);
        classToGenerate.addMethod(buildFilterSupportedAnnotations());
        classToGenerate.addMethod(buildSuperclassFieldCollectorMethod());
        classToGenerate.addMethod(buildSingleFieldValidationMethod(ifBlocksForAnnotationType));
        classToGenerate.addMethod(buildMainValidationMethod());

        try {
            JavaFile.builder("com.example.validator", classToGenerate.build()).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
