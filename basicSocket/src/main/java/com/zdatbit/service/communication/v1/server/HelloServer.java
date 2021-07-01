package com.zdatbit.service.communication.v1.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端接收客户端请求，调用hello服务，并把得到的结果返回给请求方
 */
public class HelloServer {

    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(8080);
        while(true){
            Socket socket = server.accept();
            new Thread(new HelloServer().new Handler(socket)).start();
        }
    }

    public class Handler implements Runnable{
        private Socket socket;
        public Handler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                while (true) {
                    String str = reader.readLine();
                    System.out.println("端口是："+socket.getPort()+",参数是:"+str);
                    HelloService service = new HelloService();
                    String hello = service.getHello();
                    writer.write(hello + "\n");
                    writer.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
