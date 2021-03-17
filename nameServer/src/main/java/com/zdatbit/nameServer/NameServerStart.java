package com.zdatbit.nameServer;

import com.zdatbit.nameServer.handler.client.FetchMetaBootStrap;
import com.zdatbit.nameServer.handler.server.RegisterBootStrap;

public class NameServerStart {


    public static void main(String[] args) {
        new Thread(new RegisterBootStrap()).start();
        new Thread(new FetchMetaBootStrap()).start();
    }

}
