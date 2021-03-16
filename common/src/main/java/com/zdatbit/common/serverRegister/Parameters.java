package com.zdatbit.common.serverRegister;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Parameters {

    private String paraName;
    private String type;
}
