package com.zdatbit.service.communication.v3.server;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.service.communication.v3.protocol.TransInfo3;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端，接收到客户端的请求后，调用本地服务并把结果返回给客户端
 */
public class HelloServer3 {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8080);

        while(true){
            new Thread(new HelloServer3().new Handler3(serverSocket.accept())).start();
        }
    }

    public class Handler3 implements Runnable{
        private Socket socket;
        public Handler3(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try {
                String paras = "";
                byte[] bytes = new byte[1024];
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                int len = 0;
                while((len = inputStream.read(bytes))!=-1){
                    paras+=new String(bytes,0,len);
                }
                TransInfo3 info = JSONObject.parseObject(paras,TransInfo3.class);

                Class<?> aClass = Class.forName(info.getClassName());
                Object o = aClass.newInstance();
                Method method = aClass.getMethod(info.getMethodName(),mergeClass(info.getParaTypes()));

                Object invoke = method.invoke(o, mergeArgs(info.getArgs(), mergeClass(info.getParaTypes())));

                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                outputStream.write(JSONObject.toJSONString(invoke).getBytes());
                socket.shutdownOutput();
                outputStream.flush();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public Class<?>[] mergeClass(String[] typeNames) throws Exception{
        Class<?>[] result = new Class[typeNames.length];
        for(int i=0;i<typeNames.length;i++){
            result[i] = Class.forName(typeNames[i]);
        }
        return result;
    }

    public Object[] mergeArgs(String[] args,Class<?>[] clazzs){
        Object[] result = new Object[args.length];
        for(int i=0;i<clazzs.length;i++){
            result[i] = JSONObject.parseObject(args[i],clazzs[i]);
        }
        return result;
    }
}
