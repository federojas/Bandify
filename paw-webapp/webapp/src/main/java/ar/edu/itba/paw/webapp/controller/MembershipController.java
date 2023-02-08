package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.Membership;
import ar.edu.itba.paw.model.MembershipState;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.MembershipNotFoundException;
import ar.edu.itba.paw.model.exceptions.MembershipNotOwnedException;
import ar.edu.itba.paw.model.exceptions.NotABandException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.MembershipService;
import ar.edu.itba.paw.service.RoleService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.controller.utils.PaginationLinkBuilder;
import ar.edu.itba.paw.webapp.dto.MembershipDto;
import ar.edu.itba.paw.webapp.form.MembershipEditForm;
import ar.edu.itba.paw.webapp.form.MembershipForm;
import ar.edu.itba.paw.webapp.security.exceptions.BandifyBadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.Objects;
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

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("/{id}")
    @Produces("application/vnd.membership.v1+json")
    public Response getMemberships(@PathParam("id") final long membershipId) {
        final Membership membership = membershipService.getMembershipById(membershipId).orElseThrow(MembershipNotFoundException::new);
        return Response.ok(MembershipDto.fromMembership(uriInfo, membership)).build();
    }

    @GET
    @Produces("application/vnd.membership-list.v1+json")
    public Response getUserMemberships(@QueryParam("user") final Long userId,
                                       @QueryParam("state") final String state,
                                       @QueryParam("page") @DefaultValue("1") final int page,
                                       @QueryParam("preview") @DefaultValue("false") final Boolean preview) {
        if(userId == null)
            throw new BandifyBadRequestException("Parameter 'user' is required");
        final User user = userService.getUserById(userId).orElseThrow(UserNotFoundException::new);

        final List<MembershipDto> memberships;
        final List<Membership> membershipsAux;
        if(preview)
            membershipsAux = membershipService.getUserMembershipsPreview(user);
        else if(state == null)
            membershipsAux = membershipService.getUserMemberships(user, page);
        else
            membershipsAux = membershipService.getUserMemberships(user, Enum.valueOf(MembershipState.class, state), page);

        if(membershipsAux.isEmpty()) {
            return Response.noContent().build();
        }

        memberships = membershipsAux.stream().map(m -> MembershipDto.fromMembership(uriInfo, m))
                    .collect(Collectors.toList());


        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<MembershipDto>>(memberships) {});
        PaginationLinkBuilder.getResponsePaginationLinks(
                response, uriInfo, page,  state == null?
                        membershipService.getTotalUserMembershipsPages(user) :
                        membershipService.getTotalUserMembershipsPages(user, Enum.valueOf(MembershipState.class, state)));
        return response.build();
    }

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON, })
    public Response createMembershipInvite(@Valid MembershipForm form, @QueryParam("user") final Long artistId) {
        User band =  userService.findByEmail(securityContext.getUserPrincipal().getName())
                .orElseThrow(UserNotFoundException::new);
        if(!band.isBand())
            throw new NotABandException();

        Membership.Builder membershipBuilder = new Membership.Builder(userService.getArtistById(artistId), band)
                .description(form.getDescription())
                .roles(roleService.getRolesByNames(form.getRoles()));
        Membership membership = membershipService.createMembershipInvite(membershipBuilder);
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(membership.getId())).build();
        return Response.created(uri).build();
    }

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

    @PUT
    @Path("/{id}")
    @Consumes("application/vnd.membership.v1+json")
    public Response editMembershipById(@PathParam("id") final Long id,
                                       @Valid MembershipEditForm membershipEditForm) {

        membershipService.editMembershipById(membershipEditForm.getDescription(),
                membershipEditForm.getRoles(), id);
        membershipService.changeState(id, Enum.valueOf(MembershipState.class, membershipEditForm.getState()));
        return Response.ok().build();
    }

}
