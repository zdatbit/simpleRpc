package com.zdatbit.service.communication.v4.service4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvokeRemote4 implements IInovkeRemote4{

    @Override
    public String say() {
        return "invoke remote";
    }

    @Override
    public List<String> getList() {
        List<String> result = new ArrayList<>();
        result.add("1");
        result.add("2");
        result.add("...");
        result.add("n");
        return result;
    }

    @Override
    public List<String> getList2(List<String> stringList) {
        return stringList;
    }

    @Override
    public Map<String, Integer> getMap() {
        Map<String,Integer> map = new HashMap<>();
        map.put("1",1);
        map.put("2",2);
        map.put("3",3);
        return map;
    }

    @Override
    public Map<String, Integer> getMap2(Map<String, Integer> map) {
        return map;
    }

    /********************************自定义入参****************************************/
}
