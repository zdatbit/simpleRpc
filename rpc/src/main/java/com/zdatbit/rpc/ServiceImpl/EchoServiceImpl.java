package com.zdatbit.rpc.ServiceImpl;

import com.zdatbit.rpc.IService.EchoService;

/**
 * Created by zhangdi21 on 2019/11/22.
 */
public class EchoServiceImpl implements EchoService {

    @Override
    public String ping(String ping) {
        return ping==null?"------>ping.":"ping";
    }

    @Override
    public String pong(String pong) {
        return pong==null?"-------->pong.":"pong";
    }
}
