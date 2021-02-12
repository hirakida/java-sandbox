package com.example;

import java.util.Date;
import java.util.List;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class ScriptEngineMain {

    public static void main(String[] args) {

        ScriptEngineManager manager = new ScriptEngineManager();
        List<ScriptEngineFactory> factories = manager.getEngineFactories();
        factories.forEach(factory -> {
            System.out.println("Engine: " + factory.getEngineName() + ' ' + factory.getEngineVersion());
            System.out.println("Language: " + factory.getLanguageName() + ' ' + factory.getLanguageVersion());
            System.out.println("Extensions: " + factory.getExtensions());
            System.out.println("MimeTypes: " + factory.getMimeTypes());
            System.out.println("Names: " + factory.getNames());
        });
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        try {
            engine.eval("var message = 'Hello'");
            engine.eval("print('#1: ' + message)");

            // Java object -> JS
            engine.put("date", new Date());
            engine.eval("print('#2: now : ' + date.toString())");

            engine.eval("var obj = Java.type('com.example.ScriptEngineMain');"
                        + "print('#3: ' + obj.getMessage());");
            engine.eval("obj.printMessage('Good afternoon.');");

            engine.put("message", "Good evening.");
            Object message = engine.get("message");
            System.out.println("#5: " + message);

            if (engine instanceof Invocable) {
                engine.eval("var func = function(arg) { print('#6: ' + arg);}");
                ((Invocable) engine).invokeFunction("func", "Good night.");
            }
        } catch (ScriptException | NoSuchMethodException e) {
            System.out.println(e);
        }
    }

    public static String getMessage() {
        return "Good morning.";
    }

    public static void printMessage(String message) {
        System.out.println("#4: " + message);
    }
}
