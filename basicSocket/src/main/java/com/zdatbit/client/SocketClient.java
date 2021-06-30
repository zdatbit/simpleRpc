package com.zdatbit.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {

    public static void main(String[] args) throws Exception{

    }

    public void connect(String ip,int port) throws Exception{
        Socket socket = new Socket(InetAddress.getByName(ip), port);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            while (true) {
                Scanner scanner = new Scanner(System.in);
                writer.write(scanner.nextLine() + "\n");
                writer.flush();
                System.out.println(reader.readLine());
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writer.close();
            reader.close();
        }
    }
}