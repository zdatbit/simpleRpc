package com.zdatbit.common.serverRegister;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Methods {
    /**方法**/
    private String method;
    /**方法参数**/
    private List<Object> parameters;
}
