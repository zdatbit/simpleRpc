package com.zdatbit.client;

import com.zdatbit.client.proxy.ProxyFactory;
import com.zdatbit.service.IConvertService;
import com.zdatbit.service.Person;
import com.zdatbit.service.User;

public class ConvertInvoke {

    private static IConvertService convertService = (IConvertService) ProxyFactory.createproxy(IConvertService.class,"tcp://convertService/ConvertService");


    public static void main(String[] args) {
        Person person = new Person();
        person.setAge(11).setName("张三");
        User user = convertService.conver2User(person);
        System.out.println(user);
    }
}
