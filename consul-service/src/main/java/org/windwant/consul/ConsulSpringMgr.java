package org.windwant.consul;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.ConsulRawClient;
import com.ecwid.consul.v1.agent.model.Service;

import java.util.Map;

/**
 * Created by windwant on 2016/8/19.
 */
public class ConsulSpringMgr {
    private static ConsulClient consulClient;

    static {
        ConsulRawClient client = new ConsulRawClient("192.168.7.162", 8500);
        consulClient = new ConsulClient(client);
    }

    //获取所有服务
    public static void getAllService(){
        Map<String, Service> map = consulClient.getAgentServices().getValue();
        System.out.println(map);
    }

    public static void main(String[] args) {
        getAllService();
    }
}
