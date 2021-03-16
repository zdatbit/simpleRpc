package com.zdatbit.common.annotations;

import java.lang.annotation.*;

/**
 * 在接口上标注
 * 标识是服务注册接口
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SService {
}
