package com.zdatbit.service.communication.v4.server4;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.service.communication.v3.protocol.TransInfo3;
import com.zdatbit.service.communication.v4.protocol4.TransInfo4;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class ServerHandler4 implements Runnable{
    private Socket socket;
    public ServerHandler4(Socket socket){
        this.socket = socket;
    }

    public void run(){
        try {
            String protocol = msgRecv();
            TransInfo4 info = JSONObject.parseObject(protocol, TransInfo4.class);

            Object obj = invoke(info);

            msgSend(obj);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 将本地得到的数据，通过网络返回给客户端
     * @param obj
     * @throws Exception
     */
    public void msgSend(Object obj) throws Exception{
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        outputStream.write(JSONObject.toJSONString(obj).getBytes());
        socket.shutdownOutput();
        outputStream.flush();
    }

    /**
     * 服务端调用本地服务，得到结果
     * @param info
     * @return
     * @throws Exception
     */
    public Object invoke(TransInfo4 info) throws Exception{
        Class<?> aClass = Class.forName(info.getClassName());
        Object o = aClass.newInstance();
        Method method = aClass.getMethod(info.getMethodName(),mergeClass(info.getParaTypes()));

        return method.invoke(o, mergeArgs(info.getArgs(), mergeClass(info.getParaTypes())));
    }

    /**
     * 从客户端接收到的封装的协议
     * @return
     * @throws Exception
     */
    public String msgRecv() throws Exception{
        String paras = "";
        byte[] bytes = new byte[1024];
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        int len = 0;
        while((len = inputStream.read(bytes))!=-1){
            paras+=new String(bytes,0,len);
        }
        return paras;
    }

    /**
     * 还原参数的类型
     * @param typeNames
     * @return
     * @throws Exception
     */
    public Class<?>[] mergeClass(String[] typeNames) throws Exception{
        Class<?>[] result = new Class[typeNames.length];
        for(int i=0;i<typeNames.length;i++){
            switch (typeNames[i]){
                //基本数据类型需要单独处理
                case "int":
                    result[i] = int.class;
                    break;
                case "double":
                    result[i] = double.class;break;
                case "byte":
                    result[i] = byte.class;break;
                case "short":
                    result[i] = short.class;break;
                case "char":
                    result[i] = char.class;break;
                case "long":
                    result[i] = long.class;break;
                case "float":
                    result[i] = float.class;break;
                case "boolean":
                    result[i] = boolean.class;break;
                default:
                    result[i] = Class.forName(typeNames[i]);
            }

        }
        return result;
    }

    /**
     * 还原实际的参数
     * @param args
     * @param clazzs
     * @return
     */
    public Object[] mergeArgs(String[] args,Class<?>[] clazzs){
        if(args==null){
            return null;
        }
        Object[] result = new Object[args.length];
        for(int i=0;i<clazzs.length;i++){
            result[i] = JSONObject.parseObject(args[i],clazzs[i]);
        }
        return result;
    }
}
