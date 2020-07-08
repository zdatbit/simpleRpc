package com.zdatbit.server;

import com.zdatbit.common.Config;
import com.zdatbit.common.HeartBeat;
import com.zdatbit.common.utils.PropertiesParse;

import java.util.Properties;

public class ServerStart {

    public static void main(String[] args) {
        new ServerStart().start();
    }

    private void start() {
        HeartBeat heartBeat = parseHeatBeat();

        RegistryClient client ;
        Properties properties = PropertiesParse.readProperties("classpath:config/localConfig.properties");
        String remote = properties.getProperty(Config.REMOTE_IP);
        String[] iports = remote.split(";");
        for(String iport:iports){
            String[] split = iport.split(":");
            String ip = split[0];
            String port = split[1];
            client = new RegistryClient(ip,Integer.parseInt(port),heartBeat);
            client.register();
        }
    }

    private HeartBeat parseHeatBeat() {
        //加载配置文件解析服务ip和端口号
        Properties properties = PropertiesParse.readProperties("classpath:config/localConfig.properties");
        HeartBeat heartBeat = new HeartBeat();
        String hostName = properties.getProperty(Config.HOSTNAME);
        heartBeat.setName(hostName);
        String clusterName = properties.getProperty(Config.CLUSTER_NAME);
        heartBeat.setClusterName(clusterName);
        heartBeat.setLastUpdate(System.currentTimeMillis());
        return heartBeat;
    }

}
