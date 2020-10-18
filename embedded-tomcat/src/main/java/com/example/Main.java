package com.example;

import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

public class Main {

    public static void main(String[] args) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        Context ctx = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(ctx, "Hello", new HelloServlet());
        ctx.addServletMappingDecoded("/hello", "Hello");

        tomcat.start();
        tomcat.getServer().await();
    }
}
