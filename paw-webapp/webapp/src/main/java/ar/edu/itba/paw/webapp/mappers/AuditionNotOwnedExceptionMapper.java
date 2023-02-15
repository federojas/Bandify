package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.AuditionNotOwnedException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuditionNotOwnedExceptionMapper implements ExceptionMapper<AuditionNotOwnedException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(AuditionNotOwnedException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.FORBIDDEN, e.getMessage(), uriInfo);
    }
}