package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.ApplicationNotFoundException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationNotFoundExceptionMapper implements ExceptionMapper<ApplicationNotFoundException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ApplicationNotFoundException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, e.getMessage(), uriInfo);
    }
}
