package com.zdatbit.service.communication.v4.client4;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zdatbit.service.communication.v4.protocol4.TransInfo4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class ProxyHelper implements InvocationHandler {

    private InetSocketAddress address;
    private Class interClass;
    private Class serviceClass;

    public ProxyHelper(Class interClass,Class serviceClass,InetSocketAddress address){
        this.address = address;
        this.interClass = interClass;
        this.serviceClass = serviceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Socket socket = new Socket();
        socket.connect(address);

        //這裡就有問題了,不知道怎麼傳輸對象和接收對象,先用java自帶的序列化和反序列化方式
        TransInfo4 info = mergeProtocol(method,args);
        String sendMsg = JSON.toJSONString(info);
        System.out.println(sendMsg);

        msgSend(socket,sendMsg);

        String result = msgRecv(socket);

        return JSONObject.parseObject(result,method.getReturnType());
    }


    public void parse(Method method,String result){
        Class<?> returnType = method.getReturnType();
        if(List.class.isAssignableFrom(returnType)){
            Object o = JSONObject.parseObject(result, method.getReturnType());
        }else if(Map.class.isAssignableFrom(returnType)){

        }
    }


    /**
     * 发送远端数据
     * @param socket
     * @param msg
     * @throws Exception
     */
    public void msgSend(Socket socket,String msg) throws Exception{
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        outputStream.write(msg.getBytes());
        socket.shutdownOutput();
        outputStream.flush();
    }

    /**
     * 获取从服务端得到的数据
     * @param socket
     * @return
     * @throws Exception
     */
    public String msgRecv(Socket socket) throws Exception{
        String result = "";
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        byte[] bytes = new byte[1024];
        int len = 0;
        while((len = inputStream.read(bytes))!=-1){
            result+=new String(bytes,0,len);
        }
        System.out.println(result);
        return result;
    }

    /**
     * 组装请求的类名、方法、参数类型和参数
     * @param method
     * @param args
     * @return
     */
    public TransInfo4 mergeProtocol(Method method,Object[] args){
        TransInfo4 info = new TransInfo4();
        info.setInterName(interClass.getName());
        info.setClassName(serviceClass.getName());
        info.setMethodName(method.getName());
        info.setParaTypes(paraTypes(method));
        info.setArgs(plainArgs(args));
        return info;
    }

    /**
     * 获取所有参数的全限定名，组装成数组返回
     * @param method
     * @return
     */
    public static String[] paraTypes(Method method){
        Class<?>[] parameterTypes = method.getParameterTypes();

        String[] types = new String[parameterTypes.length];
        for(int i=0;i<parameterTypes.length;i++){
            types[i] = parameterTypes[i].getName();
        }
        return types;
    }

    /**
     * 组装所有参数，以json序列化，然后组装成数组
     * @param args
     * @return
     */
    public static String[] plainArgs(Object[] args){
        if(args==null){
            return null;
        }
        String[] result = new String[args.length];
        for(int i=0;i<args.length;i++){
            result[i] = JSONObject.toJSONString(args[i]);
        }
        return result;
    }
}
