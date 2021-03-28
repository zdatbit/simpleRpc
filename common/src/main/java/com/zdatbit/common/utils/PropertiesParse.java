package com.zdatbit.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesParse {
    private static Logger logger = LoggerFactory.getLogger(PropertiesParse.class);
    /**
     * 读取解析properties文件
     */
    public static Properties readProperties(String filePath) {
        Properties properties = new Properties();
        try{
            InputStream resourceAsStream =  new FileInputStream(new File(filePath));
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