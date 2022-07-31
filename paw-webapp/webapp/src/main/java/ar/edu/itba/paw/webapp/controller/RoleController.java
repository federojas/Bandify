package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.service.RoleService;
import ar.edu.itba.paw.webapp.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("roles")
@Component
public class RoleController {
        @Autowired
        private RoleService rs;

        @Context
        private UriInfo uriInfo;

        @GET
        @Produces(value = { MediaType.APPLICATION_JSON, })
        public Response roles(@QueryParam("names") final List<String> names) {
            List<RoleDto> roles;
            if(names == null || names.isEmpty())
                roles = rs.getAll()
                        .stream().map(r -> RoleDto.fromRole(uriInfo, r)).collect(Collectors.toList());
            else
                roles = rs.getRolesByNames(names)
                        .stream().map(r -> RoleDto.fromRole(uriInfo, r)).collect(Collectors.toList());

            if(roles.isEmpty())
                return Response.noContent().build();

            Response.ResponseBuilder response = Response.ok(new GenericEntity<List<RoleDto>>(roles) {});
            return response.build();
        }
}
