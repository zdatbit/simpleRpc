package com.zdatbit.service.communication.v3.client;

import com.zdatbit.service.communication.v3.protocol.TransInfo3;
import com.zdatbit.service.communication.v3.service.HelloService3;
import com.zdatbit.service.communication.v3.service.IHelloService3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

public class HelloClient3 {

    public static void main(String[] args) {
        IHelloService3 helloService = (IHelloService3)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[]{IHelloService3.class}, new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Socket socket = new Socket();
                        socket.connect(new InetSocketAddress("localhost",8080));

                        //這裡就有問題了,不知道怎麼傳輸對象和接收對象,先用java自帶的序列化和反序列化方式
                        TransInfo3 info = new TransInfo3();
                        info.setInterName(IHelloService3.class.getName());
                        info.setClassName(HelloService3.class.getName());
                        info.setMethodName(method.getName());
                        info.setArgs(args);
                        ObjectOutputStream writer = new ObjectOutputStream(socket.getOutputStream());
                        writer.writeObject(info);
                        writer.flush();

                        Class<?> returnType = method.getReturnType();

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
