package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.DuplicateMembershipException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DuplicateMembershipExceptionMapper implements ExceptionMapper<DuplicateMembershipException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(DuplicateMembershipException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
