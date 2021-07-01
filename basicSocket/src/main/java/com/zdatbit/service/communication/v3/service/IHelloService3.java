package com.zdatbit.service.communication.v3.service;

import com.zdatbit.service.communication.v3.entity.MsgRsp;
import com.zdatbit.service.communication.v3.entity.TestEntity;

public interface IHelloService3 {

    public String hello();

    public MsgRsp getRsp(String name, TestEntity entity);
}
