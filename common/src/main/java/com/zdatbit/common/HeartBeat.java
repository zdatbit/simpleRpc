package com.zdatbit.common;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class HeartBeat {
    /**
     * ip
     */
    private String ip;
    /**
     * 端口号
     */
    private int port;
    /**
     * 机器名
     */
    private String name;
    /**
     * 集群名
     */
    private String clusterName;
    /**
     * 最后一次更新时间
     */
    private long lastUpdate;
}
