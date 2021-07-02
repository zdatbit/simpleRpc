package com.zdatbit.service.communication.v4.entity4;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

@Data
@Accessors(chain = true)
public class ReturnData<T> {

    private boolean bool;
    private byte bt;
    private int it;
    private short st;
    private long lg;
    private double dl;
    private float ft;
    private char cr;

    private List<Integer> ints;

    private Map<String,Integer> map;

    private T data;

    private List<T> lists;

    private Map<String,T> maps;

    private List<Key> keys;

    private Map<Key,Value> keyval;
}
