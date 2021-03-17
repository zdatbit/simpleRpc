package com.zdatbit.client.communication;

import com.zdatbit.client.ServiceMetaHandler;
import com.zdatbit.common.utils.CommonPool;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class Connect2Server {

    private String host;
    private String port;
    private String result;

    public Connect2Server(){}

    public Connect2Server(String host,String port){
        this.host = host;
        this.port = port;
    }

    public void conn(){

        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap client = new Bootstrap();
        try {
            client.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast(new ServiceResponseHandler(result));
                        }
                    });

            ChannelFuture f = client.connect(host, Integer.parseInt(port)).sync();
            ChannelFuture channelFuture = f.channel().writeAndFlush("\n");
        }catch(InterruptedException e){
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }
}
