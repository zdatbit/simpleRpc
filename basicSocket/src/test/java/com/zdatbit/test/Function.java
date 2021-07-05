package com.zdatbit.test;

import java.util.ArrayList;
import java.util.List;

public class Function {

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        User user1 = new User(1,"张三",20);
        User user2 = new User(2,"李四",21);
        users.add(user1);
        users.add(user2);
        return users;
    }


    public List<User> getUsers2(List<User> temp){
        List<User> users = new ArrayList<>();
        User user1 = new User(1,"张三",20);
        User user2 = new User(2,"李四",21);
        users.add(user1);
        users.add(user2);
        return users;
    }
}
