package com.zdatbit.service;

import com.zdatbit.common.annotations.SMethod;
import com.zdatbit.common.annotations.SService;

@SService
public interface ICommunication {

    @SMethod
    public void saySomething(String something);

    public void anotherThing(String another);
}
