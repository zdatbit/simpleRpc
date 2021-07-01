package com.zdatbit.service.communication.v3.server;

import com.zdatbit.service.communication.v3.protocol.TransInfo3;

import java.io.DataInput;
import java.io.DataInputStream;
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
                String paras = "";
                byte[] bytes = new byte[1024];
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                int len = 0;
                while((len = inputStream.read(bytes))!=-1){
                    System.out.println(len);
                    paras+=new String(bytes,0,len);
                    System.out.println(paras);
                }
                System.out.println("参数是："+paras);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
