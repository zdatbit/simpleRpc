package com.zdatbit.client.exception;

public class ServiceNotFoundException extends Exception{

    public ServiceNotFoundException(){
        super("未发现相应服务");
    }

    public ServiceNotFoundException(String msg){
        super(msg);
    }
}
