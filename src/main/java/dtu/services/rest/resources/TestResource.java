package dtu.services.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/ping")
public class TestResource
{
    @GET
    public Response ping()
    {
        return Response.ok("Token Service is online").build();
    }
}
