package com.zdatbit.common.serialize;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.common.protocol.CommunicationProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleSerialize {

    public Logger logger = LoggerFactory.getLogger(SimpleSerialize.class);


    /**
     * 序列化入参
     * @param params
     * @return
     */
    public String[] serializeParams(Object[] params){
        if(params==null){
            return null;
        }
        String[] paras = new String[params.length];
        for(int i=0;i<params.length;i++){
            paras[i] = JSONObject.toJSONString(params[i]);
        }
        return paras;
    }
}
