package com.zdatbit.client.communication;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.common.protocol.CommunicationProtocol;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.Inet4Address;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Connect2Server {

    private String host;
    private String port;

    ServiceResponseHandler handler = new ServiceResponseHandler();

    private ServiceRegisterEntity serviceRegisterEntity;

    private CommunicationProtocol protocol;

    public Connect2Server(){}


    public Connect2Server(ServiceRegisterEntity serviceRegisterEntity,CommunicationProtocol protocol){
        this.serviceRegisterEntity = serviceRegisterEntity;
        this.protocol = protocol;
        host = hashIp(serviceRegisterEntity.getIps());
        port = serviceRegisterEntity.getPort();
    }

    public Connect2Server(String host,String port,ServiceRegisterEntity serviceRegisterEntity){
        this.host = host;
        this.port = port;
        this.serviceRegisterEntity = serviceRegisterEntity;
    }

    public void connect(){
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
                            ch.pipeline().addLast(handler);
                        }
                    });

            client.connect("localhost", Integer.parseInt(port)).sync();
        }catch(InterruptedException e){
            e.printStackTrace();
        }//注意这里不能关闭连接
    }

    public String getResponse() throws Exception{

        ChannelPromise channelPromise = handler.sendMessage(JSONObject.toJSONString(protocol));
        channelPromise.await();
        return handler.getData();
    }

    /**
     * 随机访问一个ip
     * @param ips
     * @return
     */
    public String hashIp(Set<String> ips){
        try {
            String targetHost = Inet4Address.getLocalHost().getHostAddress();
            Random random = new Random();
            random.setSeed(Long.parseLong(String.valueOf(targetHost.hashCode())));
            int randomNum = random.nextInt(ips.size());
            Iterator<String> it = ips.iterator();
            int count = 0;
            while (it.hasNext()) {
                if (randomNum == count) {
                    return it.next();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
