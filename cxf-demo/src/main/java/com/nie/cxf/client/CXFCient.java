package com.nie.cxf.client;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import com.nie.cxf.server.CXFDemo;

/**
 * Created by aayongche on 2016/1/7.
 */
public class CXFCient {
    private static final String ADDRESS = "http://localhost:8080/cxf/services/cxfdemo";//"http://localhost:9001/cxfdemo";
    public static void main(String[] args){
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(CXFDemo.class);
        factory.setAddress(ADDRESS);
        CXFDemo client = (CXFDemo)factory.create();
        System.out.println(client.sayHello("world"));
        System.out.printf(client.getRandomUser().getName());
    }
}
