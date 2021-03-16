package com.zdatbit.common.serverRegister;

import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Method;
import java.util.List;

@Data
@Accessors(chain = true)
public class ServiceRegisterEntity {
    /**服务名**/
    private String serviceName;
    /**服务接口**/
    private List<String> serviceInter;
    /**服务实现**/
    private String serviceImpl;
    /**方法列表**/
    private List<Methods> methodsList;
    /**IP地址**/
    private List<String> ips;
    /**服务端口**/
    private String port;
}
