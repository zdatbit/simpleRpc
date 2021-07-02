package com.zdatbit.service.communication.v4.service4;

import java.util.List;
import java.util.Map;

public interface IInovkeRemote4 {

    public String say();

    /**
     * 返回一个list
     * @return
     */
    public List<String> getList();

    /**
     * 返回一个字符数组
     * @param stringList
     * @return
     */
    public List<String> getList2(List<String> stringList);

    /**
     * 返回一个map
     * @return
     */
    public Map<String,Integer> getMap();

    /**
     * 返回一个map
     * @param map
     * @return
     */
    public Map<String,Integer> getMap2(Map<String,Integer> map);
}
