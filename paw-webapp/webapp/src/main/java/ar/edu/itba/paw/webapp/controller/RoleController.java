package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.exceptions.MembershipNotFoundException;
import ar.edu.itba.paw.model.exceptions.RoleNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.AuditionService;
import ar.edu.itba.paw.service.MembershipService;
import ar.edu.itba.paw.service.RoleService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.RoleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.ws.rs.*;
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

    @Autowired
    private MembershipService membershipService;

    @Context
    private UriInfo uriInfo;



    // TODO: paginacion?
    @GET
    @Produces("application/vnd.role-list.v1+json")
    public Response roles(@QueryParam("user") final Long userId,
                          @QueryParam("audition") final Long auditionId,
                          @QueryParam("membership") final Long membershipId) {
        Set<RoleDto> roles;
        if(userId == null && auditionId == null && membershipId == null) {
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
            if (membershipId != null)
                roles.addAll(membershipService.getMembershipById(membershipId).orElseThrow(MembershipNotFoundException::new)
                        .getRoles().stream().map(r -> RoleDto.fromRole(uriInfo, r))
                        .collect(Collectors.toSet()));
        }
        if(roles.isEmpty())
            return Response.noContent().build();

        Response.ResponseBuilder response = Response.ok(new GenericEntity<Set<RoleDto>>(roles) {});
        return response.build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/vnd.role.v1+json")
    public Response getById(@PathParam("id") final long id) {
        final Role role = roleService.getRoleById(id).orElseThrow(RoleNotFoundException::new);
        return Response.ok(RoleDto.fromRole(uriInfo, role)).build();
    }

}
