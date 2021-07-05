package com.zdatbit.service.communication.v5;

import com.alibaba.fastjson.JSON;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ConnectZK {

    private static final String path = "localhost:2181";

    private String BASE_SERVICES = "/service";

    /**
     * 向zk注册服务信息
     * @param registerInfo
     */
    public void register(RegisterInfo registerInfo){
        try{
            ZooKeeper zooKeeper = new ZooKeeper(path,5000,(watchedEvent)->{});
            Stat exists = zooKeeper.exists(BASE_SERVICES + registerInfo.getServiceName(), false);
            if(exists==null) {
                //父节点持久
                zooKeeper.create(BASE_SERVICES + registerInfo.getServiceName(), "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            String serverInfo = JSON.toJSONString(registerInfo);
            zooKeeper.create(BASE_SERVICES + registerInfo.getServiceName()+"/server",serverInfo.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.EPHEMERAL_SEQUENTIAL);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
