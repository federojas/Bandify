package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.webapp.security.exceptions.ExpiredJWTException;
import ar.edu.itba.paw.webapp.security.exceptions.InvalidSignatureException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class InvalidSignatureExceptionMapper implements ExceptionMapper<InvalidSignatureException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(InvalidSignatureException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.UNAUTHORIZED, e.getMessage(), uriInfo);
    }
}