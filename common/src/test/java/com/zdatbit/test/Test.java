package com.zdatbit.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

public class Test {

//    @org.junit.Test
//    public void run(){
//        C c = new C();
//        c.setId(1).setIds("111");
//        String cString = JSON.toJSONString(c);
//        D d = JSONObject.parseObject(cString, D.class);
//        System.out.println(d.toString());
//    }

    @org.junit.Test
    public void run2(){
        Class<? extends Test> aClass = this.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        Arrays.stream(declaredMethods).forEach(method -> {
            //System.out.println(method);
            Parameter[] parameters = method.getParameters();
            Arrays.stream(parameters).forEach(parameter -> {
                String name = parameter.getName();
                System.out.println("-----------------------------------"+name);
                System.out.println(parameter.getType().getTypeName());
            });

        });
    }

    private void run3(String... s){
        System.out.println(s);
    }
}
