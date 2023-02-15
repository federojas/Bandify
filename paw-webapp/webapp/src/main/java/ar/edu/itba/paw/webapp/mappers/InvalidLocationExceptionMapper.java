package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.InvalidLocationException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidLocationExceptionMapper implements ExceptionMapper<InvalidLocationException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(InvalidLocationException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
