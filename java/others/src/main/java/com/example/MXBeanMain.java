package com.example;

import java.lang.management.CompilationMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public final class MXBeanMain {

    public static void main(String[] args) {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        System.out.println("VmName: " + runtimeMXBean.getVmName());
        System.out.println("VmVersion: " + runtimeMXBean.getVmVersion());
        System.out.println("VmVendor: " + runtimeMXBean.getVmVendor());
        System.out.println("SpecName: " + runtimeMXBean.getSpecName());
        System.out.println("Name: " + runtimeMXBean.getName());

        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        System.out.println("Name: " + compilationMXBean.getName());
    }
}
