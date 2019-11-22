package com.zdatbit.rpc.test;

import com.zdatbit.rpc.IService.EchoService;
import com.zdatbit.rpc.ServiceImpl.EchoServiceImpl;
import com.zdatbit.rpc.client.RpcClient;

import java.net.InetSocketAddress;

/**
 * Created by zhangdi21 on 2019/11/22.
 */
public class Client {

    public static void main(String[] args) {
        RpcClient<EchoService> client = new RpcClient<>();
        EchoService echoService = client.likeNativeProcess(EchoServiceImpl.class, new InetSocketAddress("localhost", 8080));
        System.err.println(echoService.ping("Are you ok?"));
        System.out.println(echoService.pong("pong"));
    }
}
