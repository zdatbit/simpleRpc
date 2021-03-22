package com.zdatbit.common.protocol;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors
public class Parameter {

    private Class<?> type;
    private Object value;
}
