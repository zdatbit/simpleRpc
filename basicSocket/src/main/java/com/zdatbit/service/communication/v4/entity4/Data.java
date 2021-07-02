package com.zdatbit.service.communication.v4.entity4;

import lombok.experimental.Accessors;

import java.util.List;

@lombok.Data
@Accessors(chain = true)
public class Data {

    private List<String> strs;
}
