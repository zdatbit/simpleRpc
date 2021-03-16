package com.zdatbit.service;

public class Communication implements ICommunication{

    @Override
    public void saySomething(String something) {
        System.out.println(something);
    }

    @Override
    public void anotherThing(String another) {
        System.out.println(another);
    }
}
