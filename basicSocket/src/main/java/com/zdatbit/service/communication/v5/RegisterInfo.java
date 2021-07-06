package com.zdatbit.service.communication.v5;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class RegisterInfo {
    /**
     * ip地址
     */
    private String address;
    /**
     * 端口号
     */
    private int port;
    /**
     * 服务名
     */
    private String serviceName;
    /**
     * 接口全限定名
     */
    private List<String> interClass;
    /**
     * 实现全限定名
     */
    private String impClass;
    /**
     * 提供服务的方法名
     */
    private List<String> method;

}
