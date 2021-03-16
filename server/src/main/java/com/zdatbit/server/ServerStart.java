package com.zdatbit.server;

import com.zdatbit.common.Config;
import com.zdatbit.common.HeartBeat;
import com.zdatbit.common.annotations.SMethod;
import com.zdatbit.common.annotations.SService;
import com.zdatbit.common.serverRegister.ServiceRegisterEntity;
import com.zdatbit.common.utils.PropertiesParse;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.*;

public class ServerStart {

    private static final String basePackage = "com.zdatbit";

    private static String serviceName;
    private static String serviceImpl;
    private static String servicePort;

    private static String remoteIP;
    private static String remotePort;

    private static Properties properties;

    static{
        properties = PropertiesParse.readProperties("config/localConfig.properties");
        serviceName = properties.getProperty("service.name");
        serviceImpl = properties.getProperty("service.impl");
        servicePort = properties.getProperty("service.port");
        remoteIP = properties.getProperty("remoteIP");
        remotePort = properties.getProperty("remotePort");
    }


    public static void main(String[] args) {
        new ServerStart().start();
    }

    private void start() {
        /**心跳**/
        HeartBeat heartBeat = parseHeatBeat();

        RegistryClient client ;

        /**服务信息**/
        ClassLoader classLoader = getClass().getClassLoader();
        Set<Class> classes = loadClass(basePackage, classLoader);
        ServiceRegisterEntity registerEntity = parseRegisterInfo(classes);
        client = new RegistryClient(remoteIP,Integer.parseInt(remotePort),heartBeat,registerEntity);
        client.register();

    }

    private HeartBeat parseHeatBeat() {
        //加载配置文件解析服务ip和端口号
        HeartBeat heartBeat = new HeartBeat();
        String hostName = properties.getProperty(Config.HOSTNAME);
        heartBeat.setName(hostName);
        String clusterName = properties.getProperty(Config.CLUSTER_NAME);
        heartBeat.setClusterName(clusterName);
        heartBeat.setLastUpdate(System.currentTimeMillis());
        return heartBeat;
    }

    public String parseDir(String currentPackage){
        File f = new File(getClass().getResource("/").getPath());
        String totalPath = f.getAbsolutePath();
        System.out.println(totalPath);
        String pageName = getClass().getPackage().getName().replace(".", "\\");
        totalPath = totalPath.replace(pageName, "");
        String packagePath = currentPackage.replace(".", "\\");
        totalPath = totalPath + "\\" + packagePath;
        return totalPath;
    }


    public List<String> parseClassName(String webPackage) {
        String packagePath = parseDir(webPackage);
        List<String> array = new ArrayList<>();
        File root = new File(packagePath);
        resolveFile(root, webPackage, array);
        return array;

    }

    private void resolveFile(File root, String webPackage, List<String> classNames) {
        if (!root.exists())
            return;
        File[] childs = root.listFiles();
        if (childs != null && childs.length > 0) {
            for (File child : childs) {
                if (child.isDirectory()) {
                    String parentPath = child.getParent();
                    String childPath = child.getAbsolutePath();
                    String temp = childPath.replace(parentPath, "");
                    temp = temp.replace("\\", "");
                    resolveFile(child, webPackage + "." + temp, classNames);
                } else {
                    String fileName = child.getName();
                    if (fileName.endsWith(".class")) {
                        String name = fileName.replace(".class", "");
                        String className = webPackage + "." + name;
                        classNames.add(className);
                    }
                }
            }
        }
    }


    public Set<Class> loadClass(String webPackage,ClassLoader classLoader){
        List<String> classString = parseClassName(webPackage);
        Set<Class> clazzs = new HashSet<>();
        classString.stream().forEach(clazz->{
            try {
                Class<?> aClass = classLoader.loadClass(clazz);
                clazzs.add(aClass);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        return clazzs;
    }

    public ServiceRegisterEntity parseRegisterInfo(Set<Class> classes){

        ServiceRegisterEntity registerEntity = new ServiceRegisterEntity();
        Class targetClass = null;
        for(Class clazz:classes){
            if(clazz.getTypeName().equals(serviceImpl)){
                targetClass = clazz;
            }
        }
        if(targetClass==null){
            return null;
        }
        Class[] interfaces = targetClass.getInterfaces();


        List<Class> targetInterface = new ArrayList<>();
        List<String> interfaceNames = new ArrayList<>();
        for(Class clazz:interfaces){
            Annotation annotation = clazz.getAnnotation(SService.class);
            if(annotation!=null){
                targetInterface.add(clazz);
                interfaceNames.add(clazz.getTypeName());
            }
        }
        if(targetInterface==null||targetInterface.size()==0){
            return null;
        }
        registerEntity.setServiceInter(interfaceNames);
        List<Method> methods = new ArrayList<>();

        for(Class clazz:targetInterface) {
            Method[] declaredMethods = clazz.getDeclaredMethods();
            Arrays.stream(declaredMethods).forEach(method -> {
                SMethod annotation1 = method.getAnnotation(SMethod.class);
                if (annotation1 != null) {
                    methods.add(method);
                }
            });
        }
        registerEntity.setMethodsList(methods);
        try {
            String hostAddress = InetAddress.getLocalHost().getHostAddress();
            registerEntity.setIps(Arrays.asList(hostAddress));
        }catch (Exception e){
            e.printStackTrace();
        }
        registerEntity.setServiceImpl(serviceImpl).setServiceName(serviceName).setPort(servicePort);
        return registerEntity;
    }

}
