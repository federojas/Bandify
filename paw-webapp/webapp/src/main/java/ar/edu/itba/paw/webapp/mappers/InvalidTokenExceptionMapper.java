package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.InvalidTokenException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidTokenExceptionMapper implements ExceptionMapper<InvalidTokenException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(InvalidTokenException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, e.getMessage(), uriInfo);
    }
}
