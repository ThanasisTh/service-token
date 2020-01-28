package dtu.services.rest.resources;

import core.TokenPool;
import dtupay.TokenRequest;
import dtupay.TokenVerify;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/api/token")
public class TokenResource
{
    @POST
    @Path("/request")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response requestTokens(TokenRequest request)
    {
        List<String> tokens = TokenPool.assignToken(request.getCprNumber(), request.getRequestedTokens());
        if (tokens == null)
        {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        else
        {
            return Response.status(Response.Status.CREATED).entity(tokens).build();
        }
    }

    @POST
    @Path("/verify")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifyToken(TokenVerify verify)
    {
        if (!TokenPool.consumeToken(verify.getCprNumber(), verify.getToken()))
        {
            return Response.status(Response.Status.UNAUTHORIZED).entity(false).build();
        }
        else
        {
            return Response.status(Response.Status.OK).entity(true).build();
        }
    }
}
