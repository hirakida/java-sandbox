package com.example;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic.Kind;

@SupportedSourceVersion(SourceVersion.RELEASE_11)
@SupportedAnnotationTypes("com.example.Hello")
public class HelloProcessor extends AbstractProcessor {
    private Messager messager;

    @Override
    public void init(ProcessingEnvironment procEnv) {
        super.init(procEnv);
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Kind.NOTE, "Processor: note");
        messager.printMessage(Kind.WARNING, "Processor: warning");

        System.out.println("annotations: " + annotations);
        for (TypeElement annotation : annotations) {
            Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(annotation);
            System.out.println("elements: " + elements);
        }
        return true;
    }
}
