package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.model.exceptions.BandsCannotApplyException;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BandsCannotApplyExceptionMapper implements ExceptionMapper<BandsCannotApplyException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(BandsCannotApplyException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
