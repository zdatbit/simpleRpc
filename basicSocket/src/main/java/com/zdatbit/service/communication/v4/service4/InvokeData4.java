package com.zdatbit.service.communication.v4.service4;

import com.zdatbit.service.communication.v4.entity4.Data;
import com.zdatbit.service.communication.v4.entity4.Key;
import com.zdatbit.service.communication.v4.entity4.ReturnData;
import com.zdatbit.service.communication.v4.entity4.Value;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InvokeData4 implements IInvokeData4{

    @Override
    public List<ReturnData<Data>> getList() {
        List<ReturnData<Data>> result = new ArrayList<>();
        ReturnData returnData1 = new ReturnData();
        Data data = new Data();
        data.setStrs(new ArrayList<String>(){{add("123");add("456");}});
        returnData1.setData(data);
        result.add(returnData1);

        List<Data> lists = new ArrayList<>();
        lists.add(data);
        lists.add(data);
        returnData1.setLists(lists);

        Key key = new Key();
        key.setKey("123");
        List<Key> keys = new ArrayList<>();
        keys.add(key);keys.add(key);
        returnData1.setKeys(keys);



        return result;
    }

    @Override
    public List<ReturnData> getList2(List<ReturnData> dataList) {
        List<ReturnData> result = new ArrayList<>();
        ReturnData returnData1 = new ReturnData();
        Data data = new Data();
        data.setStrs(new ArrayList<String>(){{add("123");add("456");}});
        returnData1.setData(data);
        result.add(returnData1);
        return result;
    }

    @Override
    public Map<Key, Value> getMap() {
        Key key = new Key();
        key.setKey("key");
        Value value = new Value();
        value.setVal("value");
        Map<Key,Value> map = new HashMap<>();
        map.put(key,value);
        return map;
    }

    @Override
    public Map<Key, Value> getMap2(List<ReturnData> dataList) {
        return null;
    }

    @Override
    public Map<Key, Value> getMap3(Map<Key, Value> maps) {
        return null;
    }
}
