package com.zdatbit.test;

import org.junit.Test;

import java.lang.reflect.Field;
import java.net.InetAddress;
import java.util.Arrays;

public class ClassLoad {

    private String something;

    @Test
    public void run(){
        Class clazz = this.getClass();
        boolean anInterface1 = clazz.isInterface();
        System.out.println(anInterface1);
        String typeName = clazz.getTypeName();
        Class[] interfaces = clazz.getInterfaces();
        Field[] declaredFields = clazz.getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            System.out.println(field);
            Class<?> type = field.getType();
            boolean anInterface = type.isInterface();
            System.out.println(anInterface);
        });
        System.out.println(typeName);
        System.out.println(this.getClass().getPackage().getName());
        System.out.println();
    }

    @Test
    public void run2() throws Exception{
        System.out.println(this.getClass().getName());
    }
}
