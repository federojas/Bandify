package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.service.RoleService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Path("roles")
@Component
public class RoleController {
        @Autowired
        private RoleService roleService;

        @Autowired
        private UserService userService;

        @Autowired
        private AuditionService auditionService;

        @Context
        private UriInfo uriInfo;

        // TODO: paginacion?
        @GET
        @Produces(value = { MediaType.APPLICATION_JSON, })
        public Response roles(@QueryParam("user") final Long userId,
                              @QueryParam("audition") final Long auditionId) {
            Set<RoleDto> roles;
            if(userId == null && auditionId == null) {
                roles = roleService.getAll()
                        .stream().map(r -> RoleDto.fromRole(uriInfo, r)).collect(Collectors.toSet());
            } else {
                roles = new HashSet<>();
                if (userId != null)
                    roles.addAll(userService.getUserById(userId).orElseThrow(UserNotFoundException::new)
                            .getUserRoles().stream().map(r -> RoleDto.fromRole(uriInfo, r))
                            .collect(Collectors.toSet()));
                if (auditionId != null)
                    roles.addAll(auditionService.getAuditionById(auditionId).getLookingFor()
                            .stream().map(r -> RoleDto.fromRole(uriInfo, r))
                            .collect(Collectors.toSet()));
            }
            if(roles.isEmpty())
                return Response.noContent().build();

            Response.ResponseBuilder response = Response.ok(new GenericEntity<Set<RoleDto>>(roles) {});
            return response.build();
        }

}
