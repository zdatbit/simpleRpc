package com.zdatbit.service.communication.v4.client4;

import com.zdatbit.service.communication.v4.entity4.Data;
import com.zdatbit.service.communication.v4.entity4.Key;
import com.zdatbit.service.communication.v4.entity4.ReturnData;
import com.zdatbit.service.communication.v4.entity4.Value;
import com.zdatbit.service.communication.v4.service4.IInovkeRemote4;
import com.zdatbit.service.communication.v4.service4.IInvokeData4;
import com.zdatbit.service.communication.v4.service4.InvokeData4;
import com.zdatbit.service.communication.v4.service4.InvokeRemote4;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 调用服务端，实现远程过程调用，复杂类型的调用
 * 采用了fastjson的序列化和反序列化
 */
public class HelloClient43 {

    public static void main(String[] args) {
        IInvokeData4 helloService = (IInvokeData4)ProxyFactory.createProxy(IInvokeData4.class, InvokeData4.class,new InetSocketAddress("localhost",8080));

        while(true) {
            System.out.println("------------------------------------------");

            List<ReturnData<Data>> list = helloService.getList();
            list.forEach(System.out::println);
            System.out.println("------------------------------------------");

            List<ReturnData> result = new ArrayList<>();
            ReturnData returnData1 = new ReturnData();
            Data data = new Data();
            data.setStrs(new ArrayList<String>(){{add("123");add("456");}});
            returnData1.setData(data);
            result.add(returnData1);
            List<ReturnData> list2 = helloService.getList2(result);
            list2.forEach(System.out::println);
            System.out.println("------------------------------------------");

            Map<Key, Value> map = helloService.getMap();
            map.forEach((key,val)->{
                System.out.println(key+"----"+val);
            });
            System.out.println("------------------------------------------");

        }
    }



}
