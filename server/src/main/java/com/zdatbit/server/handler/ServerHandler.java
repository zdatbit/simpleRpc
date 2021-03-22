package com.zdatbit.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.common.protocol.CommunicationProtocol;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;

public class ServerHandler extends SimpleChannelInboundHandler<String>{

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        try {
            CommunicationProtocol communicationProtocol = JSONObject.parseObject(s, CommunicationProtocol.class);
            if(communicationProtocol.getServiceImpl()!=null){
                String serviceImpl = communicationProtocol.getServiceImpl();
                Class<?> aClass = Class.forName(serviceImpl);
                if(communicationProtocol.getMethod()!=null){
                    try {
                        Method method = aClass.getMethod(communicationProtocol.getMethod(), communicationProtocol.getParameterTypes());
                        Object invoke = method.invoke(aClass, communicationProtocol.getParametersList());
                        System.out.println("调用结果是：" + JSONObject.toJSONString(invoke));
                        channelHandlerContext.writeAndFlush(JSONObject.toJSONString(invoke) + "\n");
                    }catch (Exception e1){
                        System.out.println("调用服务失败");
                        e1.printStackTrace();
                    }
                }
            }else{
                channelHandlerContext.writeAndFlush("调用服务为空\n");
            }
        }catch (Exception e){
            channelHandlerContext.writeAndFlush(e.getMessage()+"\n");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
