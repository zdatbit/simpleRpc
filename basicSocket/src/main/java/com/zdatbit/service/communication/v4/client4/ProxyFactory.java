package com.zdatbit.service.communication.v4.client4;


import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

public class ProxyFactory {

    public static <T> Object createProxy(Class clazz,Class service,InetSocketAddress address){
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{clazz}, new ProxyHelper(clazz,service,address));
    }
}
