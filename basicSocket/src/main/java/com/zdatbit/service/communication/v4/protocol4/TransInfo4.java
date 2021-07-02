package com.zdatbit.service.communication.v4.protocol4;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 客戶端通過遠程調用發送給服務端的數據
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class TransInfo4 implements Serializable {
    /**
     * 接口的全限定名
     */
    public String interName;
    /**
     * 調用的具體類的全限定明
     */
    public String className;
    /**
     * 調用的具體方法
     */
    public String methodName;
    /**
     * 傳參的數據類型
     */
    public String[] paraTypes;
    /**
     * 實際傳參
     */
    public String[] args;
}
