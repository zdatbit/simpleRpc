package com.zdatbit.nameServer.handler.client;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;
import com.zdatbit.nameServer.ServiceInfos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * 处理客户端的拉取信息
 */
public class ClientFetchHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("-------------------------------"+s+"---------------------------");
        Map<String, ServiceRegisterEntity> serviceInfos = ServiceInfos.serviceInfos;
        channelHandlerContext.writeAndFlush(JSONObject.toJSONString(serviceInfos)+"\n");
    }
}
