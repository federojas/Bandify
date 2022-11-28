package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.webapp.security.exceptions.ExpiredJWTException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class ExpiredJWTExceptionMapper implements ExceptionMapper<ExpiredJWTException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ExpiredJWTException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.UNAUTHORIZED, e.getMessage(), uriInfo);
    }
}