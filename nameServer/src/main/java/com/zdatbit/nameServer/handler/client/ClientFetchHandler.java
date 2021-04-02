package com.zdatbit.nameServer.handler.client;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.common.protocol.ServiceProtocol;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;
import com.zdatbit.nameServer.ServiceInfos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 处理客户端的拉取信息
 */
public class ClientFetchHandler extends SimpleChannelInboundHandler<String> {

    public Logger logger = LoggerFactory.getLogger(ClientFetchHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("-------------------------------"+s+"---------------------------");
        ServiceProtocol serviceProtocol = JSONObject.parseObject(s, ServiceProtocol.class);
        if(serviceProtocol!=null){
            ConcurrentHashMap<String, CopyOnWriteArrayList<ServiceRegisterEntity>> serviceImpls = ServiceInfos.serviceInfos.get(serviceProtocol.getServiceName());
            if(serviceImpls!=null){
                CopyOnWriteArrayList<ServiceRegisterEntity> serviceRegisterEntities = serviceImpls.get(serviceProtocol.getServiceImpl());
                channelHandlerContext.writeAndFlush(JSONObject.toJSONString(serviceRegisterEntities)+"\n");
            }else{
                logger.info("没有服务实现可以返回");
            }
        }else{
            logger.info("没有服务可以返回");
        }
    }
}
