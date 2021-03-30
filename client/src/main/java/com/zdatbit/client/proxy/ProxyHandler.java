package com.zdatbit.client.proxy;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.client.ClientStart;
import com.zdatbit.client.ServiceInfos;
import com.zdatbit.client.communication.Connect2Server;
import com.zdatbit.client.exception.ProtocolException;
import com.zdatbit.client.exception.ServiceNotFoundException;
import com.zdatbit.common.protocol.CommunicationProtocol;
import com.zdatbit.common.serialize.SimpleDeSerialize;
import com.zdatbit.common.serialize.SimpleSerialize;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProxyHandler implements InvocationHandler {

    Logger logger = LoggerFactory.getLogger(ProxyHandler.class);

    private String serviceName;
    private String serviceImpl;

    private SimpleSerialize simpleSerialize = new SimpleSerialize();
    private SimpleDeSerialize simpleDeSerialize = new SimpleDeSerialize();

    public ProxyHandler(){

    }

    public ProxyHandler(String serviceName,String serviceImpl){
        this.serviceName = serviceName;
        this.serviceImpl = serviceImpl;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        new ClientStart().start();
        //如果没有拉取到服务信息，自旋
        for(;;){
            if(ServiceInfos.registerInfos.size()>0){
                break;
            }
        }
        System.out.println("============================"+ServiceInfos.registerInfos);
        Map<String,ServiceRegisterEntity> serviceRegister = ServiceInfos.registerInfos.get(serviceName);
        if(serviceName==null||serviceName.length()==0){
            throw new ProtocolException("请检查协议格式");
        }
        if(serviceRegister==null){
            throw new ServiceNotFoundException();
        }
        if(serviceImpl==null||serviceName.length()==0){
            throw new ProtocolException("请检查协议格式");
        }

        ServiceRegisterEntity serviceRegisterEntity = serviceRegister.get(serviceImpl);
        if(serviceRegisterEntity==null){
            throw new ServiceNotFoundException("未发现注册服务");
        }else{
            //连接远程服务器
            CommunicationProtocol protocol = protocol(serviceRegisterEntity,method,args);
            Connect2Server connect2Server = new Connect2Server(serviceRegisterEntity,protocol);
            connect2Server.connect();
            System.out.println("连接之后是否走了下一步");
            String response = connect2Server.getResponse();
            System.out.println("客户端调用结果是:"+response);
            Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(method.getReturnType().getName());
            return JSONObject.parseObject(response,aClass);
        }
    }


    /**
     * 去调用服务方的通信协议，包括服务实现，方法，参数类型，参数
     * @param serviceRegisterEntity
     * @param method
     * @param params
     * @return
     */
    public CommunicationProtocol protocol(ServiceRegisterEntity serviceRegisterEntity,Method method,Object[] params){
        CommunicationProtocol protocol = new CommunicationProtocol();
        protocol.setMethod(method.getName()).setParameterTypes(combineParaTypes(method.getParameterTypes()))
                .setParametersList(simpleSerialize.serializeParams(params)).setServiceImpl(serviceRegisterEntity.getServiceImpl());
        return protocol;
    }



    public List<String> combineParaTypes(Class<?>[] classes){
        List<String> params = new ArrayList<>();
        for(Class clazz:classes){
            params.add(clazz.getName());
        }
        return params;
    }
}
