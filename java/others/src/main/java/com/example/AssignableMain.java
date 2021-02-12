package com.example;

public final class AssignableMain {

    public static void main(String[] args) {
        Test1 test1 = new Test1(1L);
        Test2 test2 = new Test2(2L, "test2");
        Test3 test3 = new Test3(3L, "test3");
        Test4 test4 = new Test4(4L, "test4");

        System.out.println(Test1.class.isAssignableFrom(test1.getClass())); // true
        System.out.println(Test1.class.isAssignableFrom(test2.getClass())); // false
        System.out.println(Test1.class.isAssignableFrom(test3.getClass())); // false
        System.out.println(Test1.class.isAssignableFrom(test4.getClass())); // true
        System.out.println("----------");

        System.out.println(Test2.class.isAssignableFrom(test1.getClass())); // false
        System.out.println(Test2.class.isAssignableFrom(test2.getClass())); // true
        System.out.println(Test2.class.isAssignableFrom(test3.getClass())); // false
        System.out.println(Test2.class.isAssignableFrom(test4.getClass())); // false
        System.out.println("----------");

        System.out.println(Test3.class.isAssignableFrom(test1.getClass())); // false
        System.out.println(Test3.class.isAssignableFrom(test2.getClass())); // false
        System.out.println(Test3.class.isAssignableFrom(test3.getClass())); // true
        System.out.println(Test3.class.isAssignableFrom(test4.getClass())); // false
        System.out.println("----------");

        System.out.println(Test4.class.isAssignableFrom(test1.getClass())); // false
        System.out.println(Test4.class.isAssignableFrom(test2.getClass())); // false
        System.out.println(Test4.class.isAssignableFrom(test3.getClass())); // false
        System.out.println(Test4.class.isAssignableFrom(test4.getClass())); // true
    }

    private static class Test1 {
        private long id;

        private Test1(long id) {this.id = id;}
    }

    private static class Test2 {
        private long id;
        private String data;

        private Test2(long id, String data) {
            this.id = id;
            this.data = data;
        }
    }

    private static class Test3 {
        private long id;
        private String data;

        private Test3(long id, String data) {
            this.id = id;
            this.data = data;
        }
    }

    private static class Test4 extends Test1 {
        private String data;

        private Test4(long id, String data) {
            super(id);
            this.data = data;
        }
    }
}
