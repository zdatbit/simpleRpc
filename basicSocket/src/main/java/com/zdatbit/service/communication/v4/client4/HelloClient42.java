package com.zdatbit.service.communication.v4.client4;

import com.zdatbit.service.communication.v4.service4.IInovkeRemote4;
import com.zdatbit.service.communication.v4.service4.InvokeRemote4;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static javax.swing.UIManager.put;

/**
 * 调用服务端，实现远程过程调用，复杂类型的调用
 * 采用了fastjson的序列化和反序列化
 */
public class HelloClient42 {

    public static void main(String[] args) {
        IInovkeRemote4 helloService = (IInovkeRemote4)ProxyFactory.createProxy(IInovkeRemote4.class, InvokeRemote4.class,new InetSocketAddress("localhost",8080));

        while(true) {
            System.out.println("------------------------------------------");

            List<String> list = helloService.getList();
            list.forEach(System.out::println);
            System.out.println("------------------------------------------");

            List<String> aNull = helloService.getList2(new ArrayList<String>() {{
                add("1");
                add("3");
                add("null");
            }});
            aNull.forEach(System.out::println);
            System.out.println("------------------------------------------");

            Map<String, Integer> map = helloService.getMap();
            map.forEach((key,val)->{
                System.out.println(key+"---->"+val);
            });
            System.out.println("------------------------------------------");
            /**
             * 入参整数，返回整数
             * @param id
             * @return
             */
            Map<String, Integer> map2 = helloService.getMap2(new HashMap<String, Integer>() {{
                put("1", 2);
                put("2", 3);
            }});
            map2.forEach((key,val)->{
                System.out.println(key+"------>"+val);
            });
            System.out.println("------------------------------------------");
            try {
                TimeUnit.SECONDS.sleep(1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }



}
