package com.zdatbit.service.communication.v4.client4;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.service.communication.v4.service4.HelloService4;
import com.zdatbit.service.communication.v4.service4.IHelloService4;
import com.zdatbit.service.communication.v4.service4.IInovkeRemote4;
import com.zdatbit.service.communication.v4.service4.InvokeRemote4;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/**
 * 调用服务端，实现远程过程调用，复杂类型的调用
 * 采用了fastjson的序列化和反序列化
 */
public class HelloClient4 {

    public static void main(String[] args) {
        IHelloService4 helloService = (IHelloService4)ProxyFactory.createProxy(IHelloService4.class, HelloService4.class,new InetSocketAddress("localhost",8080));

        while(true) {
            System.out.println("------------------------------------------");

            helloService.noArgsNoReturn();
            System.out.println("------------------------------------------");
            /**
             * 无参，有返回
             * @return
             */
            String s = helloService.noArgsReturnString();
            System.out.println("------------------------------------------"+s);
            /**
             * 无参，返回整形
             * @return
             */
            int i = helloService.noArgsReturnInt();
            System.out.println("------------------------------------------"+i);
            /**
             * 入参整数，返回整数
             * @param id
             * @return
             */
            int i1 = helloService.hasArgsReturnInt(3);
            System.out.println("------------------------------------------"+i1);


            String something = helloService.hasArgsReturnString("something");
            System.out.println("------------------------------------------"+something);
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
