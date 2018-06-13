package com.nie.cxf.outinterface;

import javax.ws.rs.core.Response;

/**
 * Created by windwant on 2016/1/8.
 */
public class CXFServiceImpl implements CXFService {
    public Response getRandomName(String firstName, String lastName) {
       String allName = firstName + "." +  lastName;
       return Response.ok(allName).build();
    }
}
