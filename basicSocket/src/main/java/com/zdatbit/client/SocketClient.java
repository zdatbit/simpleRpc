package com.zdatbit.client;

import com.zdatbit.server.SocketServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) throws Exception{
        Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 8080);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                writer.write(scanner.nextLine() + "\n");
                writer.flush();

                System.out.println(reader.readLine());
                while (reader.readLine() != null) {
                    System.out.println(reader.readLine());
                }
            }

        }catch (Exception e){
           e.printStackTrace();
        }finally {
            writer.close();
            reader.close();
        }
    }
}
