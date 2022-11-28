package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.webapp.security.exceptions.UnauthorizedException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthExceptionMapper implements ExceptionMapper<UnauthorizedException> {
    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(UnauthorizedException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.UNAUTHORIZED, e.getMessage(), uriInfo);
    }
}
