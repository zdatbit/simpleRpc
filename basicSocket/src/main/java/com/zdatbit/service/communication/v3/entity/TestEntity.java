package com.zdatbit.service.communication.v3.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class TestEntity implements Serializable {

    private String msg;
    private String name;
}
