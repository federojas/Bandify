package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dto.ApiDiscoverDto;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
import javax.ws.rs.core.*;


@Path("/")
@Component
public class ApiDiscoverController {
    @Context
    private UriInfo uriInfo;
    @GET
    @Produces("application/vnd.bandify-api.v1+json")
    public Response getApi() {
        return Response.ok(ApiDiscoverDto.getApiDiscover(uriInfo)).build();
    }
}
