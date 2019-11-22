package com.zdatbit.rpc.server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 远程服务发布者
 * Created by zhangdi21 on 2019/11/22.
 */
public class RpcServer {
    //根据当前java application 获取线程池
    static Executor executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public static void process(String hostname,int port) throws Exception{
        ServerSocket server = new ServerSocket(); //服务端
        //监听客户端的Tcp连接 根据ip地址和端口号
        server.bind(new InetSocketAddress(hostname, port));
        try{
            while(true){
                //当接收到新的客户端连接之后 封装成Task 由线程池执行
                executor.execute(new ProcessTask(server.accept()));
            }
        }finally{
            server.close();
        }
    }


    private static class ProcessTask implements Runnable{
        Socket client = null;
        public ProcessTask(Socket client) {
            this.client = client;
        }

        @Override
        public void run(){
            ObjectInputStream input = null;
            ObjectOutputStream output = null;
            try{
                //将客户端发送的码流反序列化成对象 反射调用服务实现者 获取执行结果
                input = new ObjectInputStream(client.getInputStream());
                String interfaceName = input.readUTF();
                //获取具体的处理类
                Class<?> service = Class.forName(interfaceName);
                //获取方法名
                String methodName = input.readUTF();
                //获取方法参数的class
                Class<?>[] parameterTypes = (Class<?>[])input.readObject();
                //反射获取方法
                Method method = service.getMethod(methodName, parameterTypes);
                //获取参数
                Object[] arguments = (Object[])input.readObject();
                //反射调用
                Object result = method.invoke(service.newInstance(), arguments);
                output = new ObjectOutputStream(client.getOutputStream());
                output.writeObject(result);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            //远程调用结束之后 释放Socket等连接资源 防止句柄泄露
            finally{
                if(output != null)
                    try {
                        output.close();
                    }catch (IOException e2) {
                        e2.printStackTrace();
                    }
                if(input != null)
                    try {
                        input.close();
                    }catch(IOException e) {
                        e.printStackTrace();
                    }
                if(client != null)
                    try {
                        client.close();
                    }catch (Exception e2) {
                        e2.printStackTrace();
                    }
            }
        }
    }
}
