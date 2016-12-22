package org.schreibvehler.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by awpwb on 22.04.2016.
 */
@Path("/")
public class HelloWorldResource {
    @GET
    @Path("/json")
    @Produces({ "application/json" })
    public String getHelloWorldJSON() {
        return "{\"result\":\"" + "Hello world!" + "\"}";
    }
}
