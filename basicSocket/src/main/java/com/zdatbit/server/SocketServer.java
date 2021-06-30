package com.zdatbit.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketServer {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8080);
        while (true) {
            Socket resSocket = serverSocket.accept();
            BufferedReader reader = new BufferedReader(new InputStreamReader(resSocket.getInputStream()));
            BufferedWriter outputStream = new BufferedWriter(new OutputStreamWriter(resSocket.getOutputStream()));
            try {
                while (true) {
                    System.out.println(reader.readLine());
                    Scanner scanner = new Scanner(System.in);
                    outputStream.write(scanner.nextLine() + "\n");
                    outputStream.flush();
                }
            }finally {
                reader.close();
                outputStream.close();
            }
        }
    }
}
