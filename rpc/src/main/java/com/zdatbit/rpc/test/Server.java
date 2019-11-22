package com.zdatbit.rpc.test;

import com.zdatbit.rpc.server.RpcServer;

/**
 * Created by zhangdi21 on 2019/11/22.
 */
public class Server {

    public static void main(String[] args) {
        new Thread(()->{
            try {
                RpcServer.process("localhost",8080);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
