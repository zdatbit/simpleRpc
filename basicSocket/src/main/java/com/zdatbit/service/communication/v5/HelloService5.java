package com.zdatbit.service.communication.v5;

@SimpleService
public class HelloService5 implements IHelloService{

    @SimpleMethod
    public void hello(){
        System.out.println("hello world");
    }
}
