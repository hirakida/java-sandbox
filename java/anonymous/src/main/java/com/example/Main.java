package com.example;

public final class Main {

    public static void main(String[] args) {
        foo();
        bar();
    }

    private static void foo() {
        FooImpl foo1 = new FooImpl();

        Foo foo2 = new Foo() {
            @Override
            public String getMessage() {
                return "Foo 2";
            }
        };

        Foo foo3 = () -> "Foo 3";

        FooImpl foo4 = new FooImpl() {
            @Override
            public String getMessage() {
                return "Foo 4";
            }
        };

        System.out.println(foo1.getMessage());
        System.out.println(foo2.getMessage());
        System.out.println(foo3.getMessage());
        System.out.println(foo4.getMessage());
    }

    private static void bar() {
        BarImpl bar1 = new BarImpl();

        BarImpl bar2 = new BarImpl() {
            {
                setMessage("Bar 2");
            }
        };

        Bar bar3 = new Bar() {
            private String message;

            @Override
            public void setMessage(String message) {
                this.message = message;
            }

            @Override
            public String getMessage() {
                return message;
            }

            {
                setMessage("Bar 3");
            }
        };

        BarImpl bar4 = new BarImpl() {
            @Override
            public String getMessage() {
                return super.getMessage() + " 4";
            }
        };

        System.out.println(bar1.getMessage());
        System.out.println(bar2.getMessage());
        System.out.println(bar3.getMessage());
        System.out.println(bar4.getMessage());
    }
}
