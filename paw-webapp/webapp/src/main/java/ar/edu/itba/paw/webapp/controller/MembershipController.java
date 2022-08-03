package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.MembershipNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.MembershipService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.controller.utils.PaginationLinkBuilder;
import ar.edu.itba.paw.webapp.dto.MembershipDto;
import ar.edu.itba.paw.webapp.security.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.List;
import java.util.stream.Collectors;

@Path("memberships")
@Component
public class MembershipController {

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces("application/vnd.membership.v1+json")
    public Response getMemberships(@PathParam("id") final long membershipId) {
        final Membership membership = membershipService.getMembershipById(membershipId).orElseThrow(MembershipNotFoundException::new);
        return Response.ok(MembershipDto.fromMembership(uriInfo, membership)).build();
    }

    //TODO VUELA? O COMBINAMOS?
//    @GET
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response getMembershipsByUsers(@QueryParam("artist") final Long artistId,
//                                          @QueryParam("band") final Long bandId) {
//        final User artist = userService.getUserById(artistId).orElseThrow(UserNotFoundException::new);
//        final User band = userService.getUserById(bandId).orElseThrow(UserNotFoundException::new);
//        final Membership membership = membershipService.getMembershipByUsers(band, artist).orElseThrow(MembershipNotFoundException::new);
//        return Response.ok(MembershipDto.fromMembership(uriInfo, membership)).build();
//    }

    @GET
    @Produces("application/vnd.membership-list.v1+json")
    public Response getUserMemberships(@QueryParam("user") final Long userId,
                                       @QueryParam("state") @DefaultValue("PENDING") final String state,
                                       @QueryParam("page") @DefaultValue("1") final int page) {
        if(userId == null)
            throw new BadRequestException("Parameter 'user' is required");
        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
        final List<MembershipDto> memberships = membershipService.getUserMemberships(user,
                        Enum.valueOf(MembershipState.class, state), page)
                .stream().map(m -> MembershipDto.fromMembership(uriInfo, m))
                .collect(Collectors.toList());

        if(memberships.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MembershipDto>>(memberships) {});
        PaginationLinkBuilder.getResponsePaginationLinks(response, uriInfo, page,  membershipService.getTotalUserMembershipsPages(user, Enum.valueOf(MembershipState.class, state)));
        return response.build();
    }

    //TODO VUELA? O COMBINAMOS?
//    @GET
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response getUserMembershipsPreview(@QueryParam("user") final Long userId) {
//        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
//        final List<MembershipDto> memberships = membershipService.getUserMembershipsPreview(user)
//                .stream().map(m -> MembershipDto.fromMembership(uriInfo, m))
//                .collect(Collectors.toList());
//
//        if(memberships.isEmpty()) {
//            return Response.noContent().build();
//        }
//        return Response.ok(new GenericEntity<List<MembershipDto>>(memberships) {}).build();
//    }
// TODO FALTAN LOS POST
//    @POST
//    @Consumes(value = {MediaType.APPLICATION_JSON, })
//    public Response createMembershipInvite(@Valid MembershipForm form) {
//        Membership.Builder builder = new Membership.Builder()
//                .description(form.getDescription()).roles(form.getRoles());
//    }

    @DELETE
    @Path("/{id}")
    @Produces("application/vnd.membership.v1+json")
    public Response deleteMembership(@PathParam("id") final Long id) {
        if(!membershipService.getMembershipById(id).isPresent()) {
            throw new MembershipNotFoundException();
        }
        membershipService.deleteMembership(id);
        return Response.noContent().build();
    }

    // TODO ACA PORQUE RECIBIAMOS SET DE ROLE EN EDIT? REVISAR LOS METODOS DEL SERVICIO
    @PUT
    @Path("/{id}")
    @Consumes("application/vnd.membership.v1+json")
    public Response editMembershipById(@PathParam("id") final Long id,
                                       @QueryParam("description") final String description,
                                       @QueryParam("roles") final List<String> roles,
                                       @QueryParam("state") final String state) {
        if(description == null && roles == null && state == null)
            throw new BadRequestException("Parameters 'description' 'roles' 'state' can not be all null");
        if((roles != null && !roles.isEmpty()) || description != null)
            membershipService.editMembershipById(description, roles, id);
        if(state != null)
            membershipService.changeState(id, Enum.valueOf(MembershipState.class, state));
        return Response.ok().build();
    }
}
