package com.zdatbit.server;

import com.alibaba.fastjson.JSON;
import com.zdatbit.common.Config;
import com.zdatbit.common.HeartBeat;
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
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ServerStart {

    public static void main(String[] args) {
        new ServerStart().start();
    }

    private void start() {
        HeartBeat heartBeat = parseHeatBeat();

        Properties properties = PropertiesParse.readProperties("D:\\ideaSpace\\simpleRpc\\server\\src\\main\\resources\\config\\localConfig.properties");
        String remote = properties.getProperty(Config.REMOTE_IP);
        String[] iports = remote.split(";");
        for(String iport:iports){
            String[] split = iport.split(":");
            String ip = split[0];
            String port = split[1];
            connect(ip,Integer.parseInt(port),heartBeat);
        }
    }

    private HeartBeat parseHeatBeat() {
        //加载配置文件解析服务ip和端口号
        Properties properties = PropertiesParse.readProperties("D:\\ideaSpace\\simpleRpc\\server\\src\\main\\resources\\config\\localConfig.properties");
        HeartBeat heartBeat = new HeartBeat();
        String hostName = properties.getProperty(Config.HOSTNAME);
        heartBeat.setName(hostName);
        String clusterName = properties.getProperty(Config.CLUSTER_NAME);
        heartBeat.setClusterName(clusterName);
        heartBeat.setLastUpdate(System.currentTimeMillis());
        return heartBeat;
    }


    public void connect(String host,int port,HeartBeat heartBeat){
        // 配置客户端NIO线程组
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap client = new Bootstrap();
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

            ChannelFuture f = client.connect(host, port).sync();
            CommonPool.scheduledExecutorService.scheduleAtFixedRate(()->{
                //System.out.println(JSON.toJSONString(heartBeat));
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
