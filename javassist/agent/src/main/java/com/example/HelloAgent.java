package com.example;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;

public class HelloAgent implements ClassFileTransformer {

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new HelloTransformer());
    }

    public static class HelloTransformer implements ClassFileTransformer {
        @Override
        public byte[] transform(ClassLoader loader,
                                String className,
                                Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain,
                                byte[] classfileBuffer) throws IllegalClassFormatException {
            if ("com/example/Hello".equals(className)) {
                ClassPool classPool = ClassPool.getDefault();

                try {
                    CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer));

                    CtConstructor ctConstructor = ctClass.getClassInitializer();
                    ctConstructor.insertAfter("System.out.println(\"static\");");

                    CtMethod ctMethod = ctClass.getDeclaredMethod("hello");
                    ctMethod.insertBefore("System.out.println(\"Before hello\");");
                    ctMethod.insertAfter("System.out.println(\"After hello\");");

                    return ctClass.toBytecode();
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            } else {
                return null;
            }
        }
    }
}
