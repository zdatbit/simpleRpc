package com.zdatbit.client;

import com.zdatbit.client.proxy.ProxyFactory;
import com.zdatbit.service.ICommunication;

public class ServiceInvoke {

    private static ICommunication communication = (ICommunication) ProxyFactory.createproxy(ICommunication.class,"tcp://testService/Communication");


    public static void main(String[] args) {
        String something = communication.saySomething("something");
        System.out.println(something);
    }
}
