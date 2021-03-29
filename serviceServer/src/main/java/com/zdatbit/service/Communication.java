package com.zdatbit.service;

public class Communication implements ICommunication{

    @Override
    public String saySomething(String something) {
        System.out.println(something);
        return "hello world";
    }

    @Override
    public void anotherThing(String another) {
        System.out.println(another);
    }
}
