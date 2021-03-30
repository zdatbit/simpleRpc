package com.zdatbit.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.common.protocol.CommunicationProtocol;
import com.zdatbit.common.serialize.SimpleDeSerialize;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.List;

public class ServerHandler extends SimpleChannelInboundHandler<String>{

    private Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    private SimpleDeSerialize simpleDeSerialize = new SimpleDeSerialize();

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("==========================="+s);
        try {
            CommunicationProtocol communicationProtocol = JSONObject.parseObject(s, CommunicationProtocol.class);
            if(communicationProtocol.getServiceImpl()!=null){
                String serviceImpl = communicationProtocol.getServiceImpl();
                Class<?> aClass = Class.forName(serviceImpl);
                if(communicationProtocol.getMethod()!=null){
                    try {
                        Class[] paramClass = getParamClass(communicationProtocol.getParameterTypes());
                        Method method = null;
                        if(paramClass.length==0){
                            method = aClass.getDeclaredMethod(communicationProtocol.getMethod());
                        }else{
                            method = aClass.getDeclaredMethod(communicationProtocol.getMethod(),paramClass);
                        }

                        Object invoke = method.invoke(aClass.newInstance(), simpleDeSerialize.paraList(communicationProtocol));
                        System.out.println("调用结果是：" + JSONObject.toJSONString(invoke));
                        channelHandlerContext.writeAndFlush(JSONObject.toJSONString(invoke) + "\n");
                    }catch (Exception e1){
                        System.out.println("调用服务失败");
                        e1.printStackTrace();
                    }
                }
            }else{
                channelHandlerContext.writeAndFlush("调用服务为空\n");
            }
        }catch (Exception e){
            channelHandlerContext.writeAndFlush(e.getMessage()+"\n");
        }
    }

    public Class[] getParamClass(List<String> paramTypes){
        return simpleDeSerialize.paramClasses(paramTypes);
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有客户端连接进来"+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
