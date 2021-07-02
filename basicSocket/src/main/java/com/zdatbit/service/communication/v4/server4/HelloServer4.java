package com.zdatbit.service.communication.v4.server4;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.service.communication.v3.protocol.TransInfo3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端，接收到客户端的请求后，调用本地服务并把结果返回给客户端
 */
public class HelloServer4 {

    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(8080);

        while(true){
            Thread thread = new Thread(new ServerHandler4(serverSocket.accept()));
            ExecutorHelper.executorService.execute(thread);
        }
    }

}
