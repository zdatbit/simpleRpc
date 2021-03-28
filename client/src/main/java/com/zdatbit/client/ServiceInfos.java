package com.zdatbit.client;

import com.zdatbit.common.serverRegister.ServiceRegisterEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceInfos {

    public static Map<String, ServiceRegisterEntity> registerInfos = new ConcurrentHashMap<>();
}
