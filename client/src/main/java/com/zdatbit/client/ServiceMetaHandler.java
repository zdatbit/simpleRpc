package com.zdatbit.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;

public class ServiceMetaHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //System.out.println("客服端收到服务器返回的消息"+s);
        HashMap<String, JSONObject> hashMap = JSONObject.parseObject(s, HashMap.class);
        hashMap.forEach((key,value)->{
            ServiceRegisterEntity serviceRegisterEntity = JSONObject.parseObject(String.valueOf(value), ServiceRegisterEntity.class);
            ServiceInfos.registerInfos.put(key,serviceRegisterEntity);
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
