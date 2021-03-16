package com.zdatbit.nameServer;

import com.alibaba.fastjson.JSON;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;

public class ServiceRegister extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("----------------------------"+msg);
        ServiceRegisterEntity serviceRegisterEntity = JSON.parseObject(msg, ServiceRegisterEntity.class);
        if(serviceRegisterEntity.getServiceImpl()!=null&&serviceRegisterEntity.getServiceInter()!=null&&serviceRegisterEntity.getMethodsList()!=null) {
            synchronized (ServiceRegister.class){
                if(ServiceInfos.serviceInfos.get(serviceRegisterEntity.getServiceName())!=null) {
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
        System.out.println(cause.getMessage());
        System.out.println("error发生");
    }
}
