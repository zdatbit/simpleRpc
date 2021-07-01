package com.zdatbit.service.communication.v3.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MsgRsp implements Serializable {

    private int id;
    private String code;
}
