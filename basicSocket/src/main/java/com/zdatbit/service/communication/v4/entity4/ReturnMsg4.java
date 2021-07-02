package com.zdatbit.service.communication.v4.entity4;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ReturnMsg4<T> {

    private int code;
    private String message;
    private T data;
}
