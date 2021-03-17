package com.zdatbit.client;

import com.zdatbit.common.utils.CommonPool;
import com.zdatbit.common.utils.PropertiesParse;
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

import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * 拉取nameServer里的服务信息
 */
public class ClientStart {

    private static String remoteIP = "";
    private static String remotePort = "";
    private static Properties properties;
    static{
        properties = PropertiesParse.readProperties("config/config.properties");
        remoteIP = properties.getProperty("remoteIP");
        remotePort = properties.getProperty("remotePort");
    }

    private EventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap client = new Bootstrap();

    public static void main(String[] args) {
        new ClientStart().fetchMeta();
    }

    private void fetchMeta() {
        try {
            client.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                            ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                            ch.pipeline().addLast(new ServiceMetaHandler());
                        }
                    });

            ChannelFuture f = client.connect(remoteIP, Integer.parseInt(remotePort)).sync();
            CommonPool.scheduledExecutorService.scheduleAtFixedRate(()->{
                f.channel().writeAndFlush("fetchMetadata"+"\n");
            },0,5, TimeUnit.SECONDS);
            f.channel().closeFuture().sync();
        }catch(InterruptedException e){
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }


}
