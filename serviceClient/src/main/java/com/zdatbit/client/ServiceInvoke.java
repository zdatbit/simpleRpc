package com.zdatbit.client;

import com.zdatbit.client.proxy.ProxyFactory;
import com.zdatbit.service.ICommunication;

public class ServiceInvoke {

    private static ICommunication communication = (ICommunication) ProxyFactory.createproxy(ICommunication.class,"tcp://serviceName/serviceImpl");


    public static void main(String[] args) {
        communication.saySomething("something");
    }
}
