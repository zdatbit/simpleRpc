package com.zdatbit.common.protocol;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ServiceProtocol {

    private String serviceName;
    private String serviceImpl;
}
