package ar.edu.itba.paw.webapp.mappers;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;

//@Provider
public class InternalServerErrorExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(RuntimeException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.INTERNAL_SERVER_ERROR, "Internal Server Error", uriInfo);
    }
}
