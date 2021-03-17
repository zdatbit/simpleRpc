package com.zdatbit.nameServer.handler.server;

import com.alibaba.fastjson.JSON;
import com.zdatbit.common.HeartBeat;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.net.InetSocketAddress;

/**
 * 心跳
 */
public class HeartBeatReceiveHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
//        System.out.println("**"+msg+"--");
        HeartBeat heartBeat = JSON.parseObject(msg,HeartBeat.class);
        if(heartBeat.getClusterName()!=null&&heartBeat.getLastUpdate()!=0&&heartBeat.getName()!=null) {
            System.out.println("**"+msg);
            InetSocketAddress inetAddress = (InetSocketAddress) channelHandlerContext.channel().remoteAddress();
            heartBeat.setIp(inetAddress.getAddress().getHostAddress());
            heartBeat.setPort(inetAddress.getPort());
            channelHandlerContext.writeAndFlush("消息上传成功\n");
        }else{
            channelHandlerContext.fireChannelRead(msg);
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
