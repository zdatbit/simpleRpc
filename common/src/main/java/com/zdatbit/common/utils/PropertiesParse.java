package com.zdatbit.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

public class PropertiesParse {
    private static Logger logger = LoggerFactory.getLogger(PropertiesParse.class);
    /**
     * 读取解析properties文件
     */
    public static Properties readProperties() {
        Properties properties = new Properties();
        String filePath = Class.class.getResource("/").getPath()+File.separator+"config"+File.separator+"server.properties";
        try{
            InputStream inputStream = new FileInputStream(new File(filePath));
            if(inputStream!=null) {
                properties.load(inputStream);
            }else{
                logger.error("properties文件未找到，请核对提供文件路径！");
            }
        }catch (Exception e){
            return null;
        }
        return properties;
    }

    /**
     * 读取jar包内的配置文件
     * @param path
     * @return
     */
    public static Properties readInJar(String path){
        Properties properties = new Properties();
        try{
            InputStream resourceAsStream = PropertiesParse.class.getClassLoader().getResourceAsStream(path);
            if(resourceAsStream!=null) {
                properties.load(resourceAsStream);
            }else{
                logger.error("properties文件未找到，请核对提供文件路径！");
            }
        }catch (Exception e){
            return null;
        }
        return properties;
    }
}