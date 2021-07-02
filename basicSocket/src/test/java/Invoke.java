import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Invoke {

    public int getInt(int id){
        return id;
    }


    public static void main(String[] args) throws Exception{
        Class<?> aClass = Class.forName(Invoke.class.getName());
        Object o = aClass.newInstance();
        Method getInt = aClass.getMethod("getInt", Integer.TYPE);
        Object invoke = getInt.invoke(o, 12);
        System.out.println(invoke);
    }


    @Test
    public void run(){
        Msg msg = new Msg();
        msg.setId(1).setName("test");

        Rsp rsp = new Rsp();
        rsp.setCode(200).setData("123456");
        msg.setRsp(rsp);

        List<Rsp> rspList = new ArrayList<>();
        rspList.add(rsp);
        rspList.add(rsp);

        msg.setRspList(rspList);

        Map<String,Rsp> map = new HashMap<>();
        map.put("1",rsp);
        map.put("2",rsp);
        map.put("3",rsp);
        msg.setRspMap(map);


        String str = JSONObject.toJSONString(map);

        System.out.println(str);

        Map s  = JSONObject.parseObject(str,HashMap.class);
        System.out.println();
    }
}
