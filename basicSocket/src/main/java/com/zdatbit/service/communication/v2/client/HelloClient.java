package com.zdatbit.service.communication.v2.client;

import com.alibaba.fastjson.JSON;
import com.zdatbit.service.communication.v2.protocol.TransInfo;
import com.zdatbit.service.communication.v2.service.HelloService;
import com.zdatbit.service.communication.v2.service.IHelloService;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HelloClient {

    public static void main(String[] args) {
        IHelloService helloService = (IHelloService)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{IHelloService.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = new Socket();
                        socket.connect(new InetSocketAddress("localhost",8080));

                        //這裡就有問題了,不知道怎麼傳輸對象和接收對象,先用java自帶的序列化和反序列化方式
                        TransInfo info = new TransInfo();
                        info.setInterName(IHelloService.class.getName());
                        info.setClassName(HelloService.class.getName());
                        info.setMethodName(method.getName());
                        info.setArgs(args);
                        ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
                        writer.writeObject(info);
                        writer.flush();


                        ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());

                        Object object = reader.readObject();
                        reader.close();
                        writer.close();
                        return object;
                    }
                });
        String hello = helloService.hello();
        System.out.println(hello);
    }
}
