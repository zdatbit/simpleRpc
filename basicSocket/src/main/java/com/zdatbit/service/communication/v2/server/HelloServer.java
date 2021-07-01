package com.zdatbit.service.communication.v2.server;

import com.zdatbit.service.communication.v2.protocol.TransInfo;

import java.io.*;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class HelloServer {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8080);

        while(true){
            new Thread(new HelloServer().new Handler(serverSocket.accept())).start();
        }
    }

    public class Handler implements Runnable{
        private Socket socket;
        public Handler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try {
                ObjectInputStream inputStream= new ObjectInputStream(socket.getInputStream());
                TransInfo info = (TransInfo)inputStream.readObject();
                Class<?> aClass = Class.forName(info.getClassName());
                Object o = aClass.newInstance();
                //反射得到方法的時候，重載的情況下，參數的類型怎麼獲得
                Method method = aClass.getMethod(info.getMethodName());
                Object object = method.invoke(o,info.getArgs());


                ObjectOutputStream outputStream= new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(object);
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
