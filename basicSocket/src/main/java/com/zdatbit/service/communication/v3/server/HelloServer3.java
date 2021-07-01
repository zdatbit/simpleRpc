package com.zdatbit.service.communication.v3.server;

import com.zdatbit.service.communication.v3.protocol.TransInfo3;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

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
                ObjectInputStream inputStream= new ObjectInputStream(socket.getInputStream());
                TransInfo3 info = (TransInfo3)inputStream.readObject();
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
