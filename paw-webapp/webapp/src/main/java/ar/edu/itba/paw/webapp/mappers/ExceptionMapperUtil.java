package ar.edu.itba.paw.webapp.mappers;

import ar.edu.itba.paw.webapp.dto.ErrorInfoDto;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

public class ExceptionMapperUtil {
    private ExceptionMapperUtil() {
    }

    public static Response toResponse(Response.Status status, String message, UriInfo uriInfo) {
        //TODO MIRAR COMP
        ErrorInfoDto errorInfo = new ErrorInfoDto();
        errorInfo.setStatus(status.getStatusCode());
        errorInfo.setTitle(status.getReasonPhrase());
        errorInfo.addMessage(message);
        errorInfo.setPath(uriInfo.getAbsolutePath().getPath());

        return Response.status(status).entity(errorInfo).type("application/vnd.bandify.api.v1+json").build();
    }
}

