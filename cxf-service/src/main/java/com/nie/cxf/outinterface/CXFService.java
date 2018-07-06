package com.nie.cxf.outinterface;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by windwant on 2016/1/8.
 */
@Path("/cfxsvr")
public interface CXFService {

    @GET
    @Path("/getrandom/{first}/{last}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getRandomName(@PathParam(value = "first") String firstName, @PathParam(value = "last") String lastName);
}
