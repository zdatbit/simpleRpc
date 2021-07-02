import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zdatbit.service.communication.v4.service4.InvokeData4;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Test2 {

    @Test
    public void test() throws Exception{

        ObjectMapper mapper = new ObjectMapper();

        List<Rsp> rspList = new ArrayList<>();
        Rsp rsp = new Rsp();
        rsp.setCode(200);
        rsp.setData("3455");
        rspList.add(rsp);
        rspList.add(rsp);

        String str = mapper.writeValueAsString(rspList);

        //List<Rsp> list = JSONObject.parseObject(str, new TypeToken<List<Rsp>>(){}.getType());
//
//        System.out.println(list.get(0).getData());
    }
    @Test
    public void test3() throws Exception{
        InvokeData4 invokeData4 = new InvokeData4();
        Method getList = InvokeData4.class.getMethod("getList");
        Type genericReturnType = getList.getGenericReturnType();
        System.out.println(genericReturnType);
    }

}
