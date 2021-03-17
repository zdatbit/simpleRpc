package com.zdatbit.client.exception;

public class ProtocolException extends Exception{

    public ProtocolException(){
        super("错误的数据格式");
    }

    public ProtocolException(String msg){
        super(msg);
    }

}
