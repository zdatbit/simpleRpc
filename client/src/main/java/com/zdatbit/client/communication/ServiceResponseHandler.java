package com.zdatbit.client.communication;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServiceResponseHandler extends SimpleChannelInboundHandler<String> {

    private String result;

    public ServiceResponseHandler(){

    }

    public ServiceResponseHandler(String result){
        this.result = result;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //将最后得到的结果返回
        result = s;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
