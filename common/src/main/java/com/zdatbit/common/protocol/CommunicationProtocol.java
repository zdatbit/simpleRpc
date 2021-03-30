package com.zdatbit.common.protocol;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class CommunicationProtocol {

    /**服务实现**/
    private String serviceImpl;
    /**调用的方法**/
    private String method;
    /**参数类型**/
    private List<String> parameterTypes;
    /**传入的参数**/
    private String[] parametersList;
}
