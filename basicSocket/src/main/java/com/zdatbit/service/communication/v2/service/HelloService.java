package com.zdatbit.service.communication.v2.service;

public class HelloService implements IHelloService{

    @Override
    public String hello() {
        return "something";
    }
}
