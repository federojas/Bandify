package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.MembershipNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.MembershipService;
import ar.edu.itba.paw.service.RoleService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.MembershipDto;
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

    @Autowired
    private RoleService roleService;

    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getMemberships(@PathParam("id") final long membershipId) {
        final Membership membership = membershipService.getMembershipById(membershipId).orElseThrow(MembershipNotFoundException::new);
        return Response.ok(MembershipDto.fromMembership(uriInfo, membership)).build();
    }

    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getMembershipsByUsers(@QueryParam("artist") final Long artistId,
                                          @QueryParam("band") final Long bandId) {
        final User artist = userService.getUserById(artistId).orElseThrow(UserNotFoundException::new);
        final User band = userService.getUserById(bandId).orElseThrow(UserNotFoundException::new);
        final Membership membership = membershipService.getMembershipByUsers(band, artist).orElseThrow(MembershipNotFoundException::new);
        return Response.ok(MembershipDto.fromMembership(uriInfo, membership)).build();
    }

    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUserMemberships(@QueryParam("user") final Long userId,
                                       @QueryParam("state") @DefaultValue("PENDING") final String state,
                                       @QueryParam("page") @DefaultValue("1") final int page) {
        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
        final List<MembershipDto> memberships = membershipService.getUserMemberships(user,
                        Enum.valueOf(MembershipState.class, state), page)
                .stream().map(m -> MembershipDto.fromMembership(uriInfo, m))
                .collect(Collectors.toList());

        if(memberships.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MembershipDto>>(memberships) {});
        getResponsePaginationLinks(response, page, membershipService.getTotalUserMembershipsPages(user, Enum.valueOf(MembershipState.class, state)));
        return response.build();
    }

    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getUserMembershipsPreview(@QueryParam("user") final Long userId) {
        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);
        final List<MembershipDto> memberships = membershipService.getUserMembershipsPreview(user)
                .stream().map(m -> MembershipDto.fromMembership(uriInfo, m))
                .collect(Collectors.toList());

        if(memberships.isEmpty()) {
            return Response.noContent().build();
        }
        return Response.ok(new GenericEntity<List<MembershipDto>>(memberships) {}).build();
    }
// TODO FALTAN LOS POST
//    @POST
//    @Consumes(value = {MediaType.APPLICATION_JSON, })
//    public Response createMembershipInvite(@Valid MembershipForm form) {
//        Membership.Builder builder = new Membership.Builder()
//                .description(form.getDescription()).roles(form.getRoles());
//    }

    @DELETE
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response deleteMembership(@PathParam("id") final Long id) {
        if(!membershipService.getMembershipById(id).isPresent()) {
            throw new MembershipNotFoundException();
        }
        membershipService.deleteMembership(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = { MediaType.APPLICATION_JSON, })
    public Response changeMembershipState(@PathParam("id") final Long id,
                                          @QueryParam("state") final String state) {
        Membership membership = membershipService.getMembershipById(id).orElseThrow(MembershipNotFoundException::new);
        membership = membershipService.changeState(membership, Enum.valueOf(MembershipState.class, state));
        return Response.ok().build();
    }

    // TODO ACA PORQUE RECIBIAMOS SET DE ROLE EN EDIT?
    @PUT
    @Path("/{id}")
    @Consumes(value = { MediaType.APPLICATION_JSON, })
    public Response editMembershipById(@PathParam("id") final Long id,
                                       @QueryParam("description") final String description,
                                       @QueryParam("roles") final List<String> roles) {
        Membership membership = membershipService.editMembershipById(description, roleService.getRolesByNames(roles), id);
        return Response.ok().build();
    }

    //TODO HACER CLASE UTILS?
    private void getResponsePaginationLinks(Response.ResponseBuilder response, int currentPage, int lastPage) {
        if(currentPage != 1)
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", currentPage - 1).build(), "prev");
        if(currentPage != lastPage)
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", currentPage + 1).build(), "next");
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first");
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", lastPage).build(), "last");
    }
}
