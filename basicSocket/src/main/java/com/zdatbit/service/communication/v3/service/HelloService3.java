package com.zdatbit.service.communication.v3.service;

import com.zdatbit.service.communication.v3.entity.MsgRsp;
import com.zdatbit.service.communication.v3.entity.TestEntity;

public class HelloService3 implements IHelloService3 {

    @Override
    public String hello() {
        return "something";
    }

    public MsgRsp getRsp(String name, TestEntity entity){
        MsgRsp rsp = new MsgRsp();
        rsp.setId(1);
        rsp.setCode(name);
        return rsp;
    }
}
