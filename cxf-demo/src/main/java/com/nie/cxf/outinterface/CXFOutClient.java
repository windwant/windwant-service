package com.nie.cxf.outinterface;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.jaxrs.client.WebClient;
import org.codehaus.jettison.json.JSONObject;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aayongche on 2016/1/8.
 */
public class CXFOutClient {
    public static void main(String[] args){

        List<Object> providerList = new ArrayList<Object>();
        providerList.add(new JacksonJsonProvider());

        String rst = WebClient.create(CXFRESTServer.ADDRESS)
                .path("/cfxsvr/getrandom/{fisrt}/{last}", "li", "an")//"/products")
                .accept(MediaType.APPLICATION_XML)
                .get(String.class);
        System.out.println(rst);
//        for (Object product : productList) {
//            System.out.println(product);
//        }

//        String responseMessage = client.path("getrandom/{fisrt}/{last}", "li", "an")
//                .accept(MediaType.APPLICATION_JSON)
//                .get(String.class);
//        System.out.println("responseMessage : " + responseMessage.toString());
    }
}
