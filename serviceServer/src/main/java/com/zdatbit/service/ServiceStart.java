package com.zdatbit.service;

import com.zdatbit.server.ServerStart;

public class ServiceStart {
    public static void main(String[] args) {
        //new ServerStart().start();
        String path = ClassLoader.getSystemClassLoader().getResource("").getPath();
        System.out.println(path);
    }
}
