package ar.edu.itba.paw.webapp.mappers;


import ar.edu.itba.paw.webapp.dto.ErrorInfoDto;
import ar.edu.itba.paw.webapp.dto.ValidationErrorDto;
import ar.edu.itba.paw.webapp.dto.ValidationErrorInfoDto;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(final ConstraintViolationException e) {
        ValidationErrorInfoDto errorInfo = new ValidationErrorInfoDto();
        errorInfo.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
        errorInfo.setTitle(Response.Status.BAD_REQUEST.getReasonPhrase());
        errorInfo.setPath(uriInfo.getAbsolutePath().getPath());
        final List<ValidationErrorDto> errors = e.getConstraintViolations()
                .stream().map(ValidationErrorDto::fromValidationException)
                .collect(Collectors.toList());
        errorInfo.setMessages(errors);
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorInfo).build();
    }
}
