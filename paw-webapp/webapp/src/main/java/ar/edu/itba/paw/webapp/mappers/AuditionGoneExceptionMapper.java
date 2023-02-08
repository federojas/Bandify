package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.AuditionGoneException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuditionGoneExceptionMapper implements ExceptionMapper<AuditionGoneException> {
    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(AuditionGoneException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.GONE, e.getMessage(), uriInfo);
    }
}
