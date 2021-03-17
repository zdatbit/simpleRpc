package com.zdatbit.service;


public class ServiceStart {
    /**启动本地服务**/
    public static void main(String[] args) {
        ICommunication communication = (ICommunication) ProxyFactory.createproxy(ICommunication.class,"tcp://serviceName/serviceImpl");
        communication.saySomething("hello");
    }
}
