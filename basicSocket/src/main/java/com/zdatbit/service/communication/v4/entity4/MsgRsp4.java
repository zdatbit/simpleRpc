package com.zdatbit.service.communication.v4.entity4;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class MsgRsp4 implements Serializable {

    private int id;
    private String code;
}
