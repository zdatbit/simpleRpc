package com.zdatbit.client;

import ch.qos.logback.core.util.SystemInfo;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;

import javax.sql.rowset.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ServiceInfos {

    public static final Integer DEAD_NODE = 10000;

    public static CopyOnWriteArrayList<ServiceRegisterEntity> registerInfos = new CopyOnWriteArrayList<>();


    /**从所有的服务中随机获取一个**/
    public static ServiceRegisterEntity getRandomService(){
        Random random = new Random(System.currentTimeMillis());
        int index = random.nextInt(registerInfos.size() - 1);
        return registerInfos.get(index);
    }


    public static void refreshNode(CopyOnWriteArrayList<ServiceRegisterEntity> registerInfos){
        //有可能死去的节点
        List<ServiceRegisterEntity> deadNode = new ArrayList<>();
        for(ServiceRegisterEntity serviceRegisterEntity:ServiceInfos.registerInfos){
            for(int i = 0;i<registerInfos.size();i++){
                if(i==registerInfos.size()-1){
                    deadNode.add(serviceRegisterEntity);
                }
                if(serviceRegisterEntity.getIp().equals(registerInfos.get(i).getIp())){
                    continue;
                }
            }
        }
        //新加入的节点
        List<ServiceRegisterEntity> newNode = new ArrayList<>();
        for(ServiceRegisterEntity serviceRegisterEntity:registerInfos){
            for(int i = 0;i<ServiceInfos.registerInfos.size();i++){
                if(i==registerInfos.size()-1){
                    newNode.add(serviceRegisterEntity);
                }
                if(serviceRegisterEntity.getIp().equals(registerInfos.get(i).getIp())){
                    continue;
                }
            }
        }
        List<ServiceRegisterEntity> collect = deadNode.stream().filter(node -> (System.currentTimeMillis() - node.getHeartBeat().getLastUpdate() > DEAD_NODE)).collect(Collectors.toList());
        ServiceInfos.registerInfos.removeAll(collect);

        ServiceInfos.registerInfos.addAll(newNode);

    }



}
