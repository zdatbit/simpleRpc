package com.zdatbit.nameServer;

import com.zdatbit.nameServer.handler.client.FetchMetaBootStrap;
import com.zdatbit.nameServer.handler.server.RegisterBootStrap;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NameServerStart {

    public static ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);

    static{
        executorService.scheduleAtFixedRate(()->{
            ServiceInfos.removeDeadNode();
        },0,10, TimeUnit.SECONDS);
    }



    public static void main(String[] args) {
        new Thread(new RegisterBootStrap()).start();
        new Thread(new FetchMetaBootStrap()).start();
    }

}
