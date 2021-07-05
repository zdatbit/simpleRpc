package com.zdatbit.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeserializeTes {

    @Test
    public void testList() throws Exception {
        List<User> userList = new ArrayList<>();
        User user1 = new User(1, "张三", 20);
        User user2 = new User(2, "李四", 21);
        userList.add(user1);
        userList.add(user2);

        Map<String, List<School>> maps = new HashMap<>();
        School school = new School(1, "bit");
        List<School> schools = new ArrayList<>();
        schools.add(school);
        schools.add(school);
        maps.put("name", schools);
        user1.setSchooles(maps);
        user2.setSchooles(maps);

        List<Company> companies = new ArrayList<>();
        Company<School> company1 = new Company(1, "农行");
        Company<School> company2 = new Company(2, "58");
        company1.setData(school);
        company2.setData(school);

        companies.add(company1);
        companies.add(company2);
        user1.setCompanies(companies);
        user2.setCompanies(companies);


        String str = JSON.toJSONString(user1);

//        Class<?> aClass = Class.forName(Function.class.getName());
//        Method getUsers = Function.class.getMethod("getUsers");
//        Class<?> returnType = getUsers.getReturnType();


//        List<User> users = JSON.parseArray(str,User.class);


        User user = JSON.parseObject(str, User.class);

        System.out.println(user);
    }

    @Test
    public void testList2() {
//        ListEntity entity = new ListEntity();
//        List<String> strs = new ArrayList<>();
//        strs.add("1");
//        strs.add("2");
//        entity.setLists(strs);
//
//        String str = JSON.toJSONString(entity);

//        ListEntity entity1 = JSON.parseObject(str,ListEntity.class);
//        System.out.println(entity1);
        Field[] declaredFields = ListEntity.class.getDeclaredFields();
        //顺序问题如何解决的呢
        for (Field f : declaredFields) {
            if (f.getGenericType() instanceof ParameterizedType) {
                ParameterizedType pType = (ParameterizedType) f.getGenericType();
                System.out.print("变量：" + pType.getTypeName() + "     ");
                Type[] types = pType.getActualTypeArguments();
                for (Type t : types) {
                    if(t instanceof ParameterizedType){
                        Type[] actualTypeArguments = ((ParameterizedType) t).getActualTypeArguments();
                        for(Type s:actualTypeArguments){
                            System.out.print("类型：" + s.getTypeName());
                        }
                    }
                }
            }
        }
    }
}

