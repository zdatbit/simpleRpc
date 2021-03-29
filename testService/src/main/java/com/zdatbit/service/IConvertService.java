package com.zdatbit.service;

import com.zdatbit.common.annotations.SMethod;
import com.zdatbit.common.annotations.SService;

@SService
public interface IConvertService {

    @SMethod
    public User conver2User(Person person);

    @SMethod
    public Person conver2Person(User user);
}
