package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.NotABandException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NotABandExceptionMapper implements ExceptionMapper<NotABandException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(NotABandException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.FORBIDDEN, e.getMessage(), uriInfo);
    }
}
