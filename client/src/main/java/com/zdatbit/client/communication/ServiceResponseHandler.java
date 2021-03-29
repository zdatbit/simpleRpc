package com.zdatbit.client.communication;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServiceResponseHandler extends SimpleChannelInboundHandler<String> {

    private ChannelHandlerContext ctx;

    private ChannelPromise promise;

    private String result;

    public ServiceResponseHandler(){

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.ctx = ctx;
    }

    public String getData(){
        return result;
    }

    public ChannelPromise sendMessage(String message) {
        if (ctx == null)
            throw new IllegalStateException();
        System.out.println("发送的参数是："+message);
        ChannelFuture channelFuture = ctx.channel().writeAndFlush(message+"\n");
        System.out.println("发送消息成功");
        promise = channelFuture.channel().newPromise();
        return promise;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        //将最后得到的结果返回
        System.out.println("得到的结果是："+s);
        result = s;
        promise.setSuccess();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
