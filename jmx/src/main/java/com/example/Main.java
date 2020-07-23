package com.example;

import java.lang.management.ManagementFactory;
import java.util.concurrent.TimeUnit;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public final class Main {

    public static void main(String[] args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName(Hello.class.getPackageName(), "type", Hello.class.getSimpleName());
        Hello mbean = new Hello();
        mbs.registerMBean(mbean, name);

        for (int i = 0; i < 10000; i++) {
            mbean.setName("hello" + i);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
