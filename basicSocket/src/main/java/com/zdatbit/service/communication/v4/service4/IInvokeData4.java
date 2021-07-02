package com.zdatbit.service.communication.v4.service4;

import com.zdatbit.service.communication.v4.entity4.Data;
import com.zdatbit.service.communication.v4.entity4.Key;
import com.zdatbit.service.communication.v4.entity4.ReturnData;
import com.zdatbit.service.communication.v4.entity4.Value;

import java.util.List;
import java.util.Map;

public interface IInvokeData4 {

    public List<ReturnData<Data>>  getList();

    public List<ReturnData> getList2(List<ReturnData> dataList);



    public Map<Key, Value> getMap();


    public Map<Key,Value> getMap2(List<ReturnData> dataList);


    public Map<Key,Value> getMap3(Map<Key,Value> maps);
}
