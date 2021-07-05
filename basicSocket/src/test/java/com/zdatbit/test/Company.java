package com.zdatbit.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@AllArgsConstructor
public class Company<T> {
    private int id;
    private String name;

    public Company(int id,String name){
        this.id = id;
        this.name = name;
    }

    private T data;
}
