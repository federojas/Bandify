package ar.edu.itba.paw.webapp.mappers;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class WebApplicationExceptionMapper implements ExceptionMapper<WebApplicationException> {

    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(WebApplicationException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.fromStatusCode(e.getResponse().getStatus()),
                e.getMessage(), uriInfo);
    }
}
