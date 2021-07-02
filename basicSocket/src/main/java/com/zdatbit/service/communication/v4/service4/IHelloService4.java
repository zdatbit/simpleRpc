package com.zdatbit.service.communication.v4.service4;

import com.zdatbit.service.communication.v3.entity.MsgRsp;
import com.zdatbit.service.communication.v3.entity.TestEntity;

public interface IHelloService4 {
    /**
     * 无参，无返回
     */
    public void noArgsNoReturn();

    /**
     * 无参，有返回
     * @return
     */
    public String noArgsReturnString();

    /**
     * 无参，返回整形
     * @return
     */
    public int noArgsReturnInt();

    /**
     * 入参整数，返回整数
     * @param id
     * @return
     */
    public int hasArgsReturnInt(int id);

    /**
     * 入参字符串，返回字符串
     * @param str
     * @return
     */
    public String hasArgsReturnString(String str);







    public String hello();



    public MsgRsp getRsp(String name, TestEntity entity);
}
