package com.zdatbit.service;

import java.io.InputStream;
import java.util.Properties;

public class Test {

    static{
        try {
            ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
            InputStream resourceAsStream = systemClassLoader.getResourceAsStream("classpath:config/localConfig.properties");

            Properties properties = new Properties();
            properties.load(resourceAsStream);

            String clusterName = properties.getProperty("hello");
            System.out.println(clusterName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
