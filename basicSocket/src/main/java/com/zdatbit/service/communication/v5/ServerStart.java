package com.zdatbit.service.communication.v5;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ServerStart{


    private static final String root = "com.zdatbit";

    private static final String serviceName = "helloService";

    private static final String serviceImpl = "com.zdatbit.service.communication.v5.HelloService5";


    public static void main(String[] args) throws Exception{
        ServerStart start = new ServerStart();
        start.start(start.serviceName, serviceImpl, 8080);
        while(true){
            TimeUnit.SECONDS.sleep(10);
        }

    }

    public Set<Class> getJarInfo(List<String> paths) throws Exception {
        Set<Class> jarClass = new HashSet<>();
        for(String path:paths) {
            JarFile jarFile = new JarFile(path);
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry jarEntry = entries.nextElement();
                String fullName = jarEntry.getName();
                if (fullName.indexOf("META-INF") < 0) {
                    //怎么遍历下去？jarEntry已包含所有分支，无需递归，只遍历就可
                    if (fullName.endsWith(".class")) {
                        String newName = fullName.replace("/", ".").replace(".class", "");
                        if (newName.startsWith(root)) {
                            jarClass.add(Thread.currentThread().getContextClassLoader().loadClass(newName));
                        }

                    }
                }
            }
        }
        return jarClass;
    }




    public void start(String serviceName,String serviceImpl,int port) throws Exception{
        //拼接服务实体
        URL resource = ServerStart.class.getResource("/");
        //启动服务
        ServerStart serverStart = new ServerStart();
        List<String> classString = new ArrayList<>();
        serverStart.getAlljarsPath(classString,"C:\\mywork\\basicSocket-1.0.0-SNAPSHOT.jar");
        RegisterInfo registerInfo = serverStart.parseRegisterInfo(classString);

        registerInfo.setAddress(InetAddress.getLocalHost().getHostAddress());
        registerInfo.setPort(port);
        registerInfo.setServiceName(serviceName).setImpClass(serviceImpl);

        System.out.println(registerInfo);
        ConnectZK connectZK = new ConnectZK();
        connectZK.register(registerInfo);

    }

    /**
     * 封装注册实体
     * @param classString
     * @return
     * @throws Exception
     */
    public RegisterInfo parseRegisterInfo(List<String> classString) throws Exception{
        RegisterInfo registerInfo = new RegisterInfo();
        Set<Class> clazzes = getJarInfo(classString);
        Iterator<Class> it = clazzes.iterator();
        List<String> interClass = new ArrayList<>();
        List<String> methodList = new ArrayList<>();
        while(it.hasNext()){
            Class cla = it.next();
            //从潜在的类里面获取所有的服务
            if(!cla.getName().equals(serviceImpl)){
                continue;
            }
            //为什么要判断接口，1是为了确定确实可以通过代理来提供服务，2是确保是一个服务
            Class[] interfaces = cla.getInterfaces();


            for(Class inter:interfaces){
                Annotation annotation = inter.getAnnotation(SimpleService.class);
                if(annotation!=null){
                    interClass.add(inter.getName());
                }
                Method[] methods = inter.getMethods();
                for(Method m:methods){
                    SimpleMethod annotation1 = m.getAnnotation(SimpleMethod.class);
                    if(annotation1!=null){
                        methodList.add(m.toGenericString());
                    }
                }
            }
        }
        registerInfo.setMethod(methodList);
        registerInfo.setInterClass(interClass);
        //registerInfo.set
        return registerInfo;
    }

    public void getAlljarsPath(List<String> jarPath,String path) throws Exception{
        File file = new File(path);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for(File file1:files){
                if(file1.isDirectory()){
                    getAlljarsPath(jarPath,file1.getPath());
                }else if(file1.isFile()){
                    if(file1.getName().endsWith(".jar")) {
                        jarPath.add(file1.getPath());
                    }
                }
            }
        }else if(file.isFile()){
            if(file.getName().endsWith(".jar")) {
                jarPath.add(file.getPath());
            }
        }
    }

    /**
     * 扫描特定包下的class文件，然后放入list中
     * @param filePath
     * @param root
     * @param list
     */
    public void parseClass(String filePath, String root,List<String> list){
        String rootPath = root.replace(".",File.separator);
        File file = new File(filePath+File.separator+rootPath);
        File[] files = file.listFiles();
        if(files!=null){
            for(File f:files){
                if(f.isDirectory()){
                    parseClass(filePath+File.separator+rootPath+File.separator+f.getName(),root+"."+f.getName(),list);
                }else{
                    if(f.isFile()&&f.getName().endsWith(".class")){
                        String clazz = root+"."+f.getName();
                        list.add(clazz);
                    }
                }
            }
        }

    }
}
