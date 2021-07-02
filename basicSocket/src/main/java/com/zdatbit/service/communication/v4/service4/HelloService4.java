package com.zdatbit.service.communication.v4.service4;

import com.zdatbit.service.communication.v3.entity.MsgRsp;
import com.zdatbit.service.communication.v3.entity.TestEntity;

public class HelloService4 implements IHelloService4 {

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


    @Override
    public void noArgsNoReturn() {
        System.out.println("无参，无返回");
    }

    @Override
    public String noArgsReturnString() {
        return "noArgsReturnString";
    }

    @Override
    public int noArgsReturnInt() {
        return 0;
    }

    @Override
    public int hasArgsReturnInt(int id) {
        return id;
    }

    @Override
    public String hasArgsReturnString(String str) {
        return str;
    }
}
