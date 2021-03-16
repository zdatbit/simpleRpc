package com.zdatbit.nameServer;

import com.alibaba.fastjson.JSON;
import com.zdatbit.common.HeartBeat;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.net.InetSocketAddress;

public class ServiceRegister extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println(msg);
//        ServiceRegisterEntity serviceRegisterEntity = JSON.parseObject(msg, ServiceRegisterEntity.class);
//        InetSocketAddress inetAddress = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();
//        heartBeat.setIp(inetAddress.getAddress().getHostAddress());
//        heartBeat.setPort(inetAddress.getPort());
//        System.out.println(JSON.toJSON(heartBeat));
//        channelHandlerContext.writeAndFlush("消息上传成功");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();

        System.out.println(inetSocketAddress+"离线。。。。。。");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("error发生");
    }
}
