package com.zdatbit;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesTest {

    private static final String propertyPath = "C:\\mywork\\ideaSpace\\simpleRpc\\serviceServer\\src\\main\\resources\\config\\localConfig.properties";

    @Test
    public void run(){
        try {
            InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(propertyPath);
            Properties properties = new Properties();
            InputStream inputStream = new FileInputStream(new File(propertyPath));
            properties.load(inputStream);

            String o = properties.getProperty("service.name");
            System.out.println(o);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void getPath(){
        String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        System.out.println(path);
    }
}
