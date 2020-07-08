//package com.zdatbit.server;
//
//import io.netty.bootstrap.Bootstrap;
//import io.netty.channel.Channel;
//import io.netty.channel.ChannelInitializer;
//import io.netty.channel.ChannelPipeline;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.SocketChannel;
//import io.netty.channel.socket.nio.NioSocketChannel;
//import io.netty.handler.codec.string.StringDecoder;
//import io.netty.handler.codec.string.StringEncoder;
//
//public class RegistryClient {
//
//    private static Channel ch;
//    private static Bootstrap bootstrap;
//
//
//    public void registry() {
//        NioEventLoopGroup workGroup = new NioEventLoopGroup();
//        try {
//            bootstrap = new Bootstrap();
//            bootstrap.group(workGroup)
//                    .channel(NioSocketChannel.class)
//                    .handler(new ChannelInitializer<SocketChannel>() {
//                        @Override
//                        protected void initChannel(SocketChannel ch) throws Exception {
//                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addLast(new StringDecoder());
//                            pipeline.addLast(new StringEncoder());
//                            pipeline.addLast(new ClientHeartbeatHandler());
//                        }
//                    });
//
//            // 连接服务器
//            doConnect();
//
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            workGroup.shutdownGracefully();
//        }
//    }
//}
