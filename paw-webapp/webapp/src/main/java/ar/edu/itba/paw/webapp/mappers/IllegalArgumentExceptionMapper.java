package ar.edu.itba.paw.webapp.mappers;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


//TODO MANDAS TOKEN SIN BASE64 TIRA ILLEGAL PERO CON 500 VER
@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(IllegalArgumentException e) {
        return ExceptionMapperUtil.toResponse(Response.Status.BAD_REQUEST, e.getMessage(), uriInfo);
    }
}
