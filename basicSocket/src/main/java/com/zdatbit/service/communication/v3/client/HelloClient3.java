package com.zdatbit.service.communication.v3.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdatbit.service.communication.v3.entity.MsgRsp;
import com.zdatbit.service.communication.v3.entity.TestEntity;
import com.zdatbit.service.communication.v3.protocol.TransInfo3;
import com.zdatbit.service.communication.v3.service.HelloService3;
import com.zdatbit.service.communication.v3.service.IHelloService3;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * 调用服务端，实现远程过程调用，复杂类型的调用
 * 采用了fastjson的序列化和反序列化
 */
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
                        info.setParaTypes(paraTypes(method));
                        info.setArgs(plainArgs(args));
                        String sendMsg = JSON.toJSONString(info);
                        System.out.println(sendMsg);
                        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                        outputStream.write(sendMsg.getBytes());
                        socket.shutdownOutput();
                        outputStream.flush();

//                        ObjectInputStream reader = new ObjectInputStream(socket.getInputStream());
//
//                        Object object = reader.readObject();
                        //reader.close();
                        //writer.close();
                        String result = "";
                        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                        byte[] bytes = new byte[1024];
                        int len = 0;
                        while((len = inputStream.read(bytes))!=-1){
                            result+=new String(bytes,0,len);
                        }
                        System.out.println(result);
                        return JSONObject.parseObject(result,method.getReturnType());
                    }
                });
        MsgRsp msgRsp = helloService.getRsp("hello world",new TestEntity("msg","name"));
        System.out.println(JSONObject.toJSONString(msgRsp));
    }


    public static String[] paraTypes(Method method){
        Class<?>[] parameterTypes = method.getParameterTypes();

        String[] types = new String[parameterTypes.length];
        for(int i=0;i<parameterTypes.length;i++){
            types[i] = parameterTypes[i].getName();
        }
        return types;
    }

    public static String[] plainArgs(Object[] args){
        String[] result = new String[args.length];
        for(int i=0;i<args.length;i++){
            result[i] = JSONObject.toJSONString(args[i]);
        }
        return result;
    }
}
