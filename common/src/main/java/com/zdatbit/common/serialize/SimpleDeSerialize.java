package com.zdatbit.common.serialize;

import com.alibaba.fastjson.JSONObject;
import com.zdatbit.common.protocol.CommunicationProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SimpleDeSerialize {

    private Logger logger = LoggerFactory.getLogger(SimpleDeSerialize.class);

    public Class[] paramClasses(List<String> paraClasses){
        Class[] result = new Class[paraClasses.size()];
        try {
            for (int i = 0; i < paraClasses.size(); i++) {
                Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(paraClasses.get(i));
                result[i] = aClass;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.info("类加载异常，异常原因如下："+e.getMessage());
        }
        return result;
    }

    /**
     * 反序列化成参数
     * @param communicationProtocol
     * @return
     */
    public Object[] paraList(CommunicationProtocol communicationProtocol){
        Object[] parametersList = communicationProtocol.getParametersList();
        List<String> parameterTypes = communicationProtocol.getParameterTypes();
        Object[] result = new Object[parametersList.length];
        try {
            for (int i = 0; i < parametersList.length; i++) {
                Class<?> aClass = Thread.currentThread().getContextClassLoader().loadClass(parameterTypes.get(i));
                Object o = JSONObject.parseObject((String) parametersList[i], aClass);
                result[i] = o;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }
}
