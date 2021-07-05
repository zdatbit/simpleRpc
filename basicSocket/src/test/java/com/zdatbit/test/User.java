package com.zdatbit.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class User {

    private int id;
    private String name;
    private int age;

    public User(int id,String name,int age){
        this.id = id;
        this.name = name;
        this.age = age;
    }

    List<Company> companies;

    Map<String,List<School>> schooles;
}
