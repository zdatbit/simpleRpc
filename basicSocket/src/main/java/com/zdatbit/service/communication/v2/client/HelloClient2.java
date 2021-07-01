package com.zdatbit.service.communication.v2.client;

import com.zdatbit.service.communication.v2.protocol.TransInfo2;
import com.zdatbit.service.communication.v2.service.HelloService2;
import com.zdatbit.service.communication.v2.service.IHelloService2;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HelloClient2 {

    public static void main(String[] args) {
        IHelloService2 helloService = (IHelloService2)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{IHelloService2.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = new Socket();
                        socket.connect(new InetSocketAddress("localhost",8080));

                        //這裡就有問題了,不知道怎麼傳輸對象和接收對象,先用java自帶的序列化和反序列化方式
                        TransInfo2 info = new TransInfo2();
                        info.setInterName(IHelloService2.class.getName());
                        info.setClassName(HelloService2.class.getName());
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
