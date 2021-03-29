package com.zdatbit.client.proxy;

import com.zdatbit.client.ClientStart;
import com.zdatbit.client.ServiceInfos;
import com.zdatbit.client.communication.Connect2Server;
import com.zdatbit.client.exception.ProtocolException;
import com.zdatbit.client.exception.ServiceNotFoundException;
import com.zdatbit.common.protocol.CommunicationProtocol;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ProxyHandler implements InvocationHandler {

    private String serviceName;
    private String serviceImpl;

    public ProxyHandler(){

    }

    public ProxyHandler(String serviceName,String serviceImpl){
        this.serviceName = serviceName;
        this.serviceImpl = serviceImpl;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        new ClientStart().start();
        try{
            Thread.sleep(10000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        System.out.println("-----------------------"+ServiceInfos.registerInfos.size()+"===================="+ServiceInfos.registerInfos+"*********"+serviceName);
        ServiceRegisterEntity serviceRegister = ServiceInfos.registerInfos.get(serviceName);
        if(serviceName==null||serviceName.length()==0){
            throw new ProtocolException("请检查协议格式");
        }
        if(serviceRegister==null){
            throw new ServiceNotFoundException();
        }
        if(serviceImpl==null||serviceName.length()==0){
            throw new ProtocolException("请检查协议格式");
        }

        if(serviceRegister.getServiceImpl()==null){
            throw new ServiceNotFoundException("未发现注册服务");
        }
        method.getName();
//        if(serviceRegister.getServiceImpl().equalsIgnoreCase(serviceImpl)){
            //连接远程服务器
            CommunicationProtocol protocol = protocol(serviceRegister,method,args);
            Connect2Server connect2Server = new Connect2Server(serviceRegister,protocol);
            connect2Server.connAndSendMessage();
//        }
        return null;
    }

    public CommunicationProtocol protocol(ServiceRegisterEntity serviceRegisterEntity,Method method,Object[] params){
        CommunicationProtocol protocol = new CommunicationProtocol();
        protocol.setMethod(method.getName()).setParameterTypes(method.getParameterTypes())
                .setParametersList(params).setServiceImpl(serviceRegisterEntity.getServiceImpl());
        return protocol;
    }
}
