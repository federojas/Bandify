package ar.edu.itba.paw.webapp.mappers;

import org.springframework.security.core.AuthenticationException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthExceptionMapper implements ExceptionMapper<AuthenticationException> {
    @Context
    UriInfo uriInfo;

    @Override
    public Response toResponse(AuthenticationException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.UNAUTHORIZED, e.getMessage(), uriInfo);
    }
}
