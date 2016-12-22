package org.zwackel.boundary;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class Resource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getPublic() {
        return Response.ok("Hello World!").build();
    }
}
