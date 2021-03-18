package com.example.processor;

import com.squareup.javapoet.*;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.persistence.Entity;
import javax.tools.Diagnostic;
import javax.validation.constraints.*;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class ValidationProcessor extends AbstractProcessor {

    private Filer filer;
    private Messager messager;
    private AnnotationRegistry annotationRegistry;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        filer=processingEnv.getFiler();
        messager=processingEnv.getMessager();
        annotationRegistry = fillAnnotationRegistry();
    }

    private AnnotationRegistry fillAnnotationRegistry(){
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
        Set<String> annotations = new LinkedHashSet<>();
        annotations.add(Entity.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element entityClass : roundEnv.getElementsAnnotatedWith(Entity.class)){
            if (!entityClass.getKind().equals(ElementKind.CLASS)){
                error(entityClass,"Only classes can be annotated as entities.");
                return true;
            }
            List<CodeBlock> ifBlocks = new ArrayList<>();
            List<MethodSpec> validationMethods = new ArrayList<>();

            String className = entityClass.getSimpleName() + "Validator";

            Map<Class<? extends Annotation>, List<TypeName>> fieldsWithAnnotation = iterateOverEntityClassElements(entityClass);
            fieldsWithAnnotation.putAll(iterateOverSuperClassElements(entityClass));

            for (Class<? extends Annotation> annotation : fieldsWithAnnotation.keySet()){
                MethodSpec method = ValidationMethodFactory.createValidationMethod(annotation, fieldsWithAnnotation.get(annotation));
                validationMethods.add(method);
                ifBlocks.add(ifStatementForAnnotationType(annotation));
            }
            buildModelValidationClass(className, validationMethods, ifBlocks);
        }
        return true;
    }

    private Map<Class<? extends Annotation>, List<TypeName>> iterateOverEntityClassElements(Element entityClass){
        List<? extends Element> classElements = entityClass.getEnclosedElements();
        Map<Class<? extends Annotation>, List<TypeName>> fieldsWithAnnotation = new HashMap<>();
        for (Element classElement : classElements){
            if (classElement.getKind()!=ElementKind.FIELD){
                continue;
            }

            //fill the map with Annotation - <fields types, that have the annotation>
            for (Class<? extends Annotation> annotation : annotationRegistry.getAnnotationClasses()){
                if (classElement.getAnnotation(annotation)==null){
                    continue;
                }
                List<TypeName> typeNames = new ArrayList<>();
                if (fieldsWithAnnotation.containsKey(annotation)){
                    typeNames = fieldsWithAnnotation.get(annotation);
                    typeNames.add(ClassName.get(classElement.asType()));
                    continue;
                }
                typeNames.add(ClassName.get(classElement.asType()));
                fieldsWithAnnotation.put(annotation, typeNames);
            }
        }
        return fieldsWithAnnotation;
    }
    private Map<Class<? extends Annotation>, List<TypeName>> iterateOverSuperClassElements(Element subClass){
        Map<Class<? extends Annotation>, List<TypeName>> fieldsWithAnnotation = new HashMap<>();
        TypeElement classAsTypeElement = (TypeElement) subClass;
        DeclaredType superClassAsDeclaredType = (DeclaredType) classAsTypeElement.getSuperclass();
        if (superClassAsDeclaredType!=null){
            Element superClass = superClassAsDeclaredType.asElement();
            fieldsWithAnnotation.putAll(iterateOverEntityClassElements(superClass));
        }
        return fieldsWithAnnotation;
    }

    private String createValidationMethodCall(Class<? extends Annotation> annotationClass){
        return "validate"+annotationClass.getSimpleName()+"(object, message, annotationValue)";
    }
    private CodeBlock ifStatementForAnnotationType(Class<? extends Annotation> annotationClass){
        return CodeBlock
                .builder()
                .beginControlFlow("if(annotation.annotationType().equals($T.class))", annotationClass)
                .addStatement(createValidationMethodCall( annotationClass))
                .addStatement("return")
                .endControlFlow()
                .build();
    }

    private CodeBlock accessPrivateFieldsBlock(){
        //get the fields from super class
        return CodeBlock
                .builder()
                .addStatement("$T<$T, $T<$T>> fieldAnnotationsMap = new $T<>()", Map.class, Object.class, List.class, Annotation.class, HashMap.class)
                .addStatement("$T[] fieldsArray = item.getClass().getDeclaredFields()",Field.class)
                .addStatement("$T<$T> fields = new $T<>()",List.class, Field.class, ArrayList.class)
                .addStatement("fields.addAll($T.asList(fieldsArray))", Arrays.class)
                .addStatement("Class<?> superClass = item.getClass().getSuperclass()")
                .beginControlFlow("if(superClass!=null)")
                .addStatement("fields.addAll(Arrays.asList(superClass.getDeclaredFields()))")
                .endControlFlow()
                .beginControlFlow("for ($T field : fields)", Field.class)
                .beginControlFlow("if(field.getName().equals(\"id\"))")
                .addStatement("continue")
                .endControlFlow()
                .addStatement("field.setAccessible(true)")
                .beginControlFlow("if (field.getAnnotations().length>0)")
                .addStatement("List<Annotation> annotations = putNotNullFirst(field)")
                .addStatement("fieldAnnotationsMap.put(field.get(item), annotations)")
                .endControlFlow()
                .endControlFlow()
                .build();
    }
    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }

    private MethodSpec buildPutNotNullFirstMethod(){
        return MethodSpec
                .methodBuilder("putNotNullFirst")
                .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(List.class, Annotation.class))
                .addParameter(ClassName.get(Field.class), "field")
                .addStatement("int indexOfNotNull = 0")
                .addStatement("$T<$T> annotations = $T.stream(field.getDeclaredAnnotations())" +
                        ".filter(annotation -> " +
                        "annotation.annotationType().getPackage().getName().startsWith(\"javax.validation.constraints\")" +
                        ").collect($T.toList())", List.class, Annotation.class, Arrays.class, Collectors.class)
                .beginControlFlow("for ($T annotation : annotations)", Annotation.class)
                .beginControlFlow("if(annotation.annotationType().equals($T.class))", NotNull.class)
                .addStatement("indexOfNotNull = annotations.indexOf(annotation)")
                .addStatement("break")
                .endControlFlow()
                .endControlFlow()
                .addStatement("Annotation temp = annotations.get(0)")
                .addStatement("annotations.set(0, annotations.get(indexOfNotNull))")
                .addStatement("annotations.set(indexOfNotNull, temp)")
                .addStatement("return annotations")
                .build();
    }

    private MethodSpec buildSingleFieldValidationMethod(List<CodeBlock> ifStatements){
        MethodSpec.Builder methodSpec = MethodSpec
                .methodBuilder("validateField")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addException(IllegalAccessException.class)
                .returns(ClassName.VOID)
                .addException(NoSuchMethodException.class)
                .addException(InvocationTargetException.class)
                .addParameter(ClassName.get(Object.class), "object")
                .addParameter(ClassName.get(Annotation.class), "annotation")
                .addStatement("String message = annotation.annotationType().getMethod(\"message\").invoke(annotation).toString()")
                .addStatement("Object annotationValue = null")
                .beginControlFlow("try")
                .addStatement("annotationValue = annotation.annotationType().getMethod(\"value\").invoke(annotation)")
                .endControlFlow()
                .beginControlFlow("catch($T e)", NoSuchMethodException.class)
                .addStatement("System.out.println(\"Annotation doesn't have value() method.\")")
                .endControlFlow();

        for (CodeBlock block : ifStatements){
            methodSpec.addCode(block);
        }

        return methodSpec.build();
    }
    private MethodSpec buildMainValidationMethod(){
        return MethodSpec
                .methodBuilder("validate")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addException(IllegalAccessException.class)
                .addException(NoSuchMethodException.class)
                .addException(InvocationTargetException.class)
                .returns(ClassName.VOID)
                .addParameter(ClassName.get(Object.class), "item")
                .addCode(accessPrivateFieldsBlock())
                .beginControlFlow("for (Object object : fieldAnnotationsMap.keySet())")
                .beginControlFlow("for(Annotation annotation : fieldAnnotationsMap.get(object))")
                .addStatement("validateField(object, annotation)")
                .endControlFlow()
                .endControlFlow()
                .build();
    }
    private void buildModelValidationClass(String className, List<MethodSpec> validationMethods, List<CodeBlock> ifBlocksForAnnotationType){
        TypeSpec.Builder positiveAnnotationClass = TypeSpec  //public <ClassName>
                .classBuilder(className)
                .addModifiers(Modifier.PUBLIC);

        positiveAnnotationClass.addMethods(validationMethods);
        positiveAnnotationClass.addMethod(buildPutNotNullFirstMethod());
        positiveAnnotationClass.addMethod(buildSingleFieldValidationMethod(ifBlocksForAnnotationType));
        positiveAnnotationClass.addMethod(buildMainValidationMethod());


        try {
            JavaFile.builder("org", positiveAnnotationClass.build()).build().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
