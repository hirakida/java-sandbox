package com.example;

public class ClassNameMain {

    public static void main(String[] args) {
        System.out.println("simpleName: " + Model.class.getSimpleName());
        System.out.println("simpleName: " + ModelExt.class.getSimpleName());
        System.out.println("simpleName: " + MyInterfaceImpl.class.getSimpleName());
        System.out.println("simpleName: " + MyInterface.class.getSimpleName());

        System.out.println("name: " + Model.class.getName());
        System.out.println("name: " + ModelExt.class.getName());
        System.out.println("name: " + MyInterfaceImpl.class.getName());
        System.out.println("name: " + MyInterface.class.getName());

        System.out.println("canonicalName: " + Model.class.getCanonicalName());
        System.out.println("canonicalName: " + ModelExt.class.getCanonicalName());
        System.out.println("canonicalName: " + MyInterfaceImpl.class.getCanonicalName());
        System.out.println("canonicalName: " + MyInterface.class.getCanonicalName());

        System.out.println("typeName: " + Model.class.getTypeName());
        System.out.println("typeName: " + ModelExt.class.getTypeName());
        System.out.println("typeName: " + MyInterfaceImpl.class.getTypeName());
        System.out.println("typeName: " + MyInterface.class.getTypeName());
    }

    private static class Model {
        private long id;

        public long getId() {
            return id;
        }
    }

    private static class ModelExt extends Model {
        private String data;

        public String getData() {
            return data;
        }
    }

    public static class MyInterfaceImpl implements MyInterface {
        private long id;

        @Override
        public long getId() {
            return id;
        }
    }

    public interface MyInterface {
        long getId();
    }
}
