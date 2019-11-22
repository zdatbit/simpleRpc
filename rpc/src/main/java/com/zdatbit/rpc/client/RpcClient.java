package com.zdatbit.rpc.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * rpc客户端本地服务代理
 * Created by zhangdi21 on 2019/11/22.
 */
public class RpcClient<S> {
    /**
     * @param serviceClass
     * @param addr
     * @return
     */
    public S likeNativeProcess(final Class<?> serviceClass,final InetSocketAddress addr)
    {    
        //将本地的接口调用转换成jdk的动态代理，在动态代理中实现接口的远程调用
        return (S) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[] {serviceClass.getInterfaces()[0]}, new InvocationHandler() {
            
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //创建Socket客户端,根据指定地址连接远程服务提供者
                Socket socket = null;
                ObjectOutputStream output = null;
                ObjectInputStream input = null;
                try {
                    socket = new Socket();
                    socket.connect(addr);
                    output = new ObjectOutputStream(socket.getOutputStream());
                    //将远程调用所需的接口类、方法名、参数列表等编码后发送给服务提供者。
                    output.writeUTF(serviceClass.getName());
                    output.writeUTF(method.getName());
                    output.writeObject(method.getParameterTypes());
                    output.writeObject(args);
                    input = new ObjectInputStream(socket.getInputStream());
                    return input.readObject();
                }finally {
                    if(socket != null)
                        socket.close();
                    if(output != null)
                        output.close();
                    if(input != null)
                        input.close();
                }
            }
        });
    }
}