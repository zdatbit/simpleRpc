package com.zdatbit.test;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class C{

    private int id;
    private String ids;
}
