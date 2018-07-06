package org.windwant.rest;

import org.windwant.rest.dao.RestDao;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/")
public class RestService {


    @Path("/hello/{name}")
    @GET
    @Produces("application/json")
    public String readStudent(@PathParam("name") String name) {
        System.out.println(new RestDao().selectStudent(name));
        return "hello " + name;
    }
}
