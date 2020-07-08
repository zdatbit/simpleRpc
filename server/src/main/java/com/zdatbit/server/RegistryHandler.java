package com.zdatbit.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.channels.SocketChannel;

public class RegistryHandler extends SimpleChannelInboundHandler<SocketChannel> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, SocketChannel socketChannel) throws Exception {

    }
}
