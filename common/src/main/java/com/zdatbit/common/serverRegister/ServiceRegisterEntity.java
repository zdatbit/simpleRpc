package com.zdatbit.common.serverRegister;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@Accessors(chain = true)
public class ServiceRegisterEntity {
    /**服务名**/
    private String serviceName;
    /**服务接口**/
    private List<String> serviceInter;
    /**服务实现**/
    //todo 这里有可能有多个实现，需要确定去调用哪个方法
    private String serviceImpl;
    /**方法列表**/
    private List<Methods> methodsList;
    /**IP地址**/
    private Set<String> ips;
    /**服务端口**/
    private String port;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceRegisterEntity that = (ServiceRegisterEntity) o;
        return Objects.equals(serviceName, that.serviceName) && Objects.equals(serviceInter, that.serviceInter) && Objects.equals(serviceImpl, that.serviceImpl) && Objects.equals(methodsList, that.methodsList) && Objects.equals(ips, that.ips) && Objects.equals(port, that.port);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, serviceInter, serviceImpl, methodsList, ips, port);
    }
}
