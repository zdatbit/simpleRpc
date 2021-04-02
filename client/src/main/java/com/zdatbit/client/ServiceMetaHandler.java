package com.zdatbit.client;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServiceMetaHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //System.out.println("客服端收到服务器返回的消息"+s);
        CopyOnWriteArrayList<ServiceRegisterEntity> serviceList = JSONObject.parseObject(s,CopyOnWriteArrayList.class);
        ServiceInfos.refreshNode(serviceList);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }


    public String simpleImpl(String serviceImpl){
        int index = serviceImpl.lastIndexOf(".");
        return serviceImpl.substring(index+1);
    }
}
