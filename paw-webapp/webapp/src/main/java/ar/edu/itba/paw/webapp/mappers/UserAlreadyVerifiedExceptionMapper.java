package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.UserAlreadyVerifiedException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UserAlreadyVerifiedExceptionMapper implements ExceptionMapper<UserAlreadyVerifiedException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(UserAlreadyVerifiedException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.CONFLICT, e.getMessage(), uriInfo);
    }
}

