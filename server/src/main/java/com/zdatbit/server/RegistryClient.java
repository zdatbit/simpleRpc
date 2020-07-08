package com.zdatbit.server;

import com.alibaba.fastjson.JSON;
import com.zdatbit.common.HeartBeat;
import com.zdatbit.common.utils.CommonPool;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

public class RegistryClient{
    private EventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap client = new Bootstrap();
    private String ipAddress;
    private int port;
    private HeartBeat heartBeat;

    public RegistryClient(){

    }

    public RegistryClient(String ipAddress,int port,HeartBeat heartBeat){
        this.ipAddress = ipAddress;
        this.port = port;
        this.heartBeat = heartBeat;
    }

    public void register(){
        try {
            client.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast(new HeartBeatSend());
                        }
                    });

            ChannelFuture f = client.connect(ipAddress, port).sync();
            CommonPool.scheduledExecutorService.scheduleAtFixedRate(()->{
                heartBeat.setLastUpdate(System.currentTimeMillis());
                f.channel().writeAndFlush(JSON.toJSONString(heartBeat));
            },10,5, TimeUnit.SECONDS);
            f.channel().closeFuture().sync();
        }catch(InterruptedException e){
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
