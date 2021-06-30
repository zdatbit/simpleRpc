package com.zdatbit.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class SocketServer {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket resSocket = serverSocket.accept();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(resSocket.getInputStream()));
//            BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(resSocket.getOutputStream()));
//            System.out.println(reader.readLine());
//            Scanner scanner = new Scanner(System.in);
//            outputStream.write(scanner.nextLine() + "\n");
//            outputStream.flush();
//            reader.close();
//            outputStream.close();
            new Thread(new Handler(resSocket)).start();
        }
    }

    public static class Handler implements Runnable{
        private Socket socket;

        public Handler(Socket socket){
            this.socket = socket;
        }

        public void run(){
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                while(true) {
                    System.out.println(reader.readLine());
                    Scanner scanner = new Scanner(System.in);
                    outputStream.write(scanner.nextLine() + "\n");
                    outputStream.flush();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
