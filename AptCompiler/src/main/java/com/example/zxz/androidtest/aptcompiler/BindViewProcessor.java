package com.example.zxz.androidtest.aptcompiler;

import com.example.zxz.androidtest.aptannotation.AutoCreate;
//import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

//@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
//        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR, "hello worldasdfsadfs!!!!!!!!!!!!!!!");
        processingEnvironment.getMessager().printMessage(Diagnostic.Kind.WARNING, "hello worldasdfsadfs!!!!!!!!!!!!!!!");
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        //定义需要处理的注解
        return Collections.singleton(AutoCreate.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations == null || annotations.size() == 0) {
            return false;
        }
        System.out.println("------------------------------roundEnv:"+roundEnv);
        // 判断元素的类型为Class
        for (Element element : roundEnv.getElementsAnnotatedWith(AutoCreate.class)) {
            System.out.println("------------------------------");
            // 判断元素的类型为Class
            if (element.getKind() == ElementKind.CLASS) {
                // 显示转换元素类型
                TypeElement typeElement = (TypeElement) element;
                // 输出元素名称
                System.out.println(typeElement.getSimpleName());
                // 输出注解属性值
//                System.out.println(typeElement.getAnnotation(AutoCreate.class).value());
            }
            System.out.println("------------------------------");
        }


        System.out.println("###########");

        //如何处理该注解
        /*
         * hello.java
          public final class HelloWorld {
             public static void main(String[] args) {
            System.out.println("Hello, JavaPoet!");
           }
            }
         */
        //文件
        //文件内容--java代码生成工具javapoet
        //MethodSpec:定义方法
        //TypeSec:定义类
        //JavaFile：生成.java文件
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();
        JavaFile javaFile = JavaFile.builder("com.example.aptproject", helloWorld)
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
//
//    @Override
//    public synchronized void init(ProcessingEnvironment processingEnvironment) {
//        super.init(processingEnvironment);
//        System.out.println("BindViewProcessor init");
//        System.err.println("BindViewProcessor init");
//    }
//
//    @Override
//    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
//        System.out.println("start process");
//        return false;
//    }
}
