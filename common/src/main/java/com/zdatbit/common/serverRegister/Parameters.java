package com.zdatbit.common.serverRegister;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Parameters {
    /**参数名**/
    private String paraName;
    /**参数的全限定名**/
    private String type;
}
