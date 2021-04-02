package com.zdatbit.nameServer;

import com.zdatbit.common.serverRegister.ServiceRegisterEntity;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServiceInfos {

    public static final Integer DEAD_TME= 10000;


    public static Map<String, ConcurrentHashMap<String,CopyOnWriteArrayList<ServiceRegisterEntity>>> serviceInfos = new ConcurrentHashMap<>();

    /**删除通信失败的节点**/
    public static void removeDeadNode(){
        for(String key:serviceInfos.keySet()){
            ConcurrentHashMap<String,CopyOnWriteArrayList<ServiceRegisterEntity>> maps = serviceInfos.get(key);
            for(String serviceImpl:maps.keySet()){
                CopyOnWriteArrayList<ServiceRegisterEntity> serviceRegisterEntities = maps.get(serviceImpl);
                for(ServiceRegisterEntity service:serviceRegisterEntities) {
                    if (System.currentTimeMillis() - service.getHeartBeat().getLastUpdate() > DEAD_TME) {
                        serviceRegisterEntities.remove(service);
                    }
                }
            }
        }
    }

    /**加入新进的节点**/
    public static void addNewNode(ServiceRegisterEntity serviceRegisterEntity){
        synchronized (ServiceInfos.class) {
            if (ServiceInfos.serviceInfos.get(serviceRegisterEntity.getServiceName()) == null) {
                ConcurrentHashMap<String,CopyOnWriteArrayList<ServiceRegisterEntity>> serviceImpl = new ConcurrentHashMap<>();
                CopyOnWriteArrayList<ServiceRegisterEntity> registerEntityList = new CopyOnWriteArrayList<>();
                registerEntityList.add(serviceRegisterEntity);
                serviceImpl.put(serviceRegisterEntity.getServiceImpl(),registerEntityList);
                ServiceInfos.serviceInfos.put(serviceRegisterEntity.getServiceName(), serviceImpl);
            } else {
                ConcurrentHashMap<String, CopyOnWriteArrayList<ServiceRegisterEntity>> serviceImpl = ServiceInfos.serviceInfos.get(serviceRegisterEntity.getServiceName());
                for(String service:serviceImpl.keySet()){
                    CopyOnWriteArrayList<ServiceRegisterEntity> registerEntityList = serviceImpl.get(service);
                    ServiceRegisterEntity registerEntity = sameService(registerEntityList, serviceRegisterEntity);
                    if (registerEntity != null) {
                        registerEntity.getHeartBeat().setLastUpdate(serviceRegisterEntity.getHeartBeat().getLastUpdate());
                    } else {
                        registerEntityList.add(serviceRegisterEntity);
                    }
                }
            }
        }
    }

    private static ServiceRegisterEntity sameService(List<ServiceRegisterEntity> registerEntityList,ServiceRegisterEntity registerEntity){
        for(ServiceRegisterEntity serviceRegisterEntity:registerEntityList){
            //如果IP相同，端口相同，则认为是相同的
            if(registerEntity.getIp().equals(serviceRegisterEntity.getIp())&&registerEntity.getPort().equals(serviceRegisterEntity.getPort())){
                return serviceRegisterEntity;
            }
        }
        return null;
    }
}
