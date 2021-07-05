package com.zdatbit.test;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListEntity {
    private List<List<User>> lists;
}
