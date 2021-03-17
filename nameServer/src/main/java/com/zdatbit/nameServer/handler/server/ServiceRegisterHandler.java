package com.zdatbit.nameServer.handler.server;

import com.alibaba.fastjson.JSON;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;
import com.zdatbit.nameServer.ServiceInfos;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;

/**
 * 服务信息注册
 */
public class ServiceRegisterHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("----------------------------"+msg);
        ServiceRegisterEntity serviceRegisterEntity = JSON.parseObject(msg, ServiceRegisterEntity.class);
        if(serviceRegisterEntity.getServiceImpl()!=null&&serviceRegisterEntity.getServiceInter()!=null&&serviceRegisterEntity.getMethodsList()!=null) {
            synchronized (ServiceRegisterHandler.class){
                if(ServiceInfos.serviceInfos.get(serviceRegisterEntity.getServiceName())==null) {
                    ServiceInfos.serviceInfos.put(serviceRegisterEntity.getServiceName(), serviceRegisterEntity);
                }else{
                    ServiceRegisterEntity registerEntity = ServiceInfos.serviceInfos.get(serviceRegisterEntity.getServiceName());
                    registerEntity.getIps().addAll(serviceRegisterEntity.getIps());
                }

            }
            channelHandlerContext.writeAndFlush("服务信息上传成功\n");
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();

        System.out.println(inetSocketAddress+"离线。。。。。。");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        System.out.println("error发生");
    }
}
