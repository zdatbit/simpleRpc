package com.zdatbit.service;


public class ConvertService implements IConvertService{

    @Override
    public User conver2User(Person person) {
        return new User().setUsername("lisi").setAge(21);
    }

    @Override
    public Person conver2Person(User user) {
        return new Person().setName("lisi").setAge(21);
    }
}
