package com.zdatbit.service.communication.v1.client;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * 从输入终端输入请求，得到服务端返回的数据
 */
public class HelloClient {

    public static void main(String[] args) throws Exception{
        Socket socket = new Socket(InetAddress.getByName("localhost"),8080);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        while(true){
            Scanner scanner = new Scanner(System.in);
            writer.write(scanner.nextLine()+"\n");
            writer.flush();
            String result = reader.readLine();
            System.out.println(result);
        }

    }
}
