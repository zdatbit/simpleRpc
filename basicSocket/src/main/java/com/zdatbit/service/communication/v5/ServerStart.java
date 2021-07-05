package com.zdatbit.service.communication.v5;

public class ServerStart {

    public static void main(String[] args) {
        //类加载器加载所有的服务，然后启动服务
        String path = System.getProperty("user.dir");
        System.out.println(path);

    }

    /**
     * 扫描所有类，加载class
     */
    public void loadClass(){

    }
}
