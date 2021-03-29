package com.zdatbit;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.service.User;

public class Test {

    @org.junit.Test
    public void run() throws Exception{
        User user = new User();
        user.setAge(11).setUsername("zhangsan");

        String str = JSONObject.toJSONString(user);

        Class<?> aClass = Class.forName("com.zdatbit.service.User");
        Object o = JSONObject.parseObject(str, aClass);
        System.out.println(o);

    }

    public User getUser() throws Exception{
        User user = new User();
        user.setAge(11).setUsername("zhangsan");

        String str = JSONObject.toJSONString(user);

        Class<?> aClass = Class.forName("com.zdatbit.service.User");
        return (User)JSONObject.parseObject(str, aClass);
    }
}
