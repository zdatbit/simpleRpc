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
        File file = new File(filePath);
        if(file.exists()) {
            InputStream inStream = null;
            try {
                inStream = new FileInputStream(file);
                properties.load(inStream);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(inStream != null) {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }else {
            logger.error("properties文件未找到，请核对提供文件路径！");
            return null;
        }
        return properties;
    }
}