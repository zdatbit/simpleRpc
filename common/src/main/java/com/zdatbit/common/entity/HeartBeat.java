package com.zdatbit.common.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Objects;

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
    private String port;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeartBeat heartBeat = (HeartBeat) o;
        return port == heartBeat.port && lastUpdate == heartBeat.lastUpdate && Objects.equals(ip, heartBeat.ip) && Objects.equals(name, heartBeat.name) && Objects.equals(clusterName, heartBeat.clusterName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ip, port, name, clusterName, lastUpdate);
    }
}
