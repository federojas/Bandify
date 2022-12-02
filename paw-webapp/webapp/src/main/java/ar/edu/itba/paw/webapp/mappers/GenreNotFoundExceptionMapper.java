package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.GenreNotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenreNotFoundExceptionMapper implements ExceptionMapper<GenreNotFoundException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(GenreNotFoundException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, e.getMessage(), uriInfo);
    }
}
