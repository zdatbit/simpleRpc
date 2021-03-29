package com.zdatbit.client.proxy;

import com.zdatbit.client.exception.ProtocolException;

import java.lang.reflect.Proxy;

public class ProxyFactory {


    public static <T> Object createproxy(Class<?> clazz, String s) {
        String serviceName = getServiceName(s);
        String serviceImpl = getServiceImpl(s);
        System.out.println("*********************************"+serviceImpl);
        Object o = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{clazz}, new ProxyHandler(serviceName, serviceImpl));
        return o;
    }


    private static String parseProtocol(String protocol) throws Exception {
        if(protocol==null||protocol.length()==0){
            throw new ProtocolException("协议格式出错");
        }
        if(protocol.startsWith("tcp://")){
            return protocol.substring(6);
        }else{
            throw new ProtocolException();
        }

    }

    private static String getServiceName(String protocol){
        try {
            String s = parseProtocol(protocol);
            String[] split = s.split("/");
            return split[0];
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private static String getServiceImpl(String protocol){
        try {
            String s = parseProtocol(protocol);
            String[] split = s.split("/");
            return split[1];
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
