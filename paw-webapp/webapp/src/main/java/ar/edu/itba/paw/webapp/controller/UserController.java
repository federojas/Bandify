package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.ApplicationState;
import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.SocialMedia;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.SocialMediaNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.ApplicationService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.controller.utils.PaginationLinkBuilder;
import ar.edu.itba.paw.webapp.dto.ApplicationDto;
import ar.edu.itba.paw.webapp.dto.SocialMediaDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.ArtistEditForm;
import ar.edu.itba.paw.webapp.form.UserArtistForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("users")
@Component
public class UserController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @Context
    private UriInfo uriInfo;


    // TODO: Rehacer el userform para que sea unico para bandas y artistas
    // por ahora solo admite artistas para poder probar el post
    @POST
    @Consumes("application/vnd.bandify.api.v1+json")
    public Response createUser(@Valid UserArtistForm form) {
        User.UserBuilder builder = new User.UserBuilder(form.getEmail(), form.getPassword(),
                form.getName(), form.isBand(), false).surname(form.getSurname());
        final User user = userService.create(builder);
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(user.getId())).build();
        return Response.created(uri).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/vnd.bandify.api.v1+json")
    public Response updateUser(@Valid ArtistEditForm form, @PathParam("id") final long id) {
        //TODO SECURITY
        //TODO FORM = NULL EXCEPTION?
        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);

        userService.editUser(user.getId(), form.getName(), form.getSurname(), form.getDescription(),
                form.getMusicGenres(), form.getLookingFor(), form.getProfileImage().getBytes(), form.getLocation());

        return Response.ok().build();
    }


    @GET
    @Path("/{id}")
    @Produces("application/vnd.bandify.api.v1+json")
    public Response getById(@PathParam("id") final long id) {
        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        return Response.ok(UserDto.fromUser(uriInfo, user)).build();
    }

    @GET
    @Path("/{id}/profile-image")
    public Response getUserProfileImage(@PathParam("id") final long id) throws IOException {
        return Response.ok(new ByteArrayInputStream(userService.getProfilePicture(id))).build();
    }


    @GET
    @Produces("application/vnd.bandify.api.v1+json")
    public Response usersSearch(@QueryParam("page") @DefaultValue("1") final int page,
                                @QueryParam("query") @DefaultValue("") final String query,
                                @QueryParam("genre") final List<String> genres,
                                @QueryParam("role") final List<String> roles,
                                @QueryParam("location")  final List<String> locations) {
        FilterOptions filter = new FilterOptions.FilterOptionsBuilder().
                withGenres(genres)
                .withRoles(roles)
                .withLocations(locations)
                .withTitle(query).build();
        List<UserDto> users = userService.filter(filter, page)
                .stream().map(u -> UserDto.fromUser(uriInfo, u)).collect(Collectors.toList());
        if(users.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserDto>>(users) {});
        PaginationLinkBuilder.getResponsePaginationLinks(response, uriInfo, page, userService.getFilterTotalPages(filter));
        return response.build();
    }

    // TODO: seguridad
    // TODO: las aplicaciones que da son por defecto las pendientes
    // le podes pasar para que te de las del estado que quieras, podemos dejarlo asi
    // o que por defecto te de las que sean de cualquier estado.
    @GET
    @Path("/{id}/applications")
    @Produces("application/vnd.bandify.api.v1+json")
    public Response getUserApplications(@PathParam("id") final long id,
                                        @QueryParam("state") @DefaultValue("PENDING") final String state,
                                        @QueryParam("page") @DefaultValue("1") final int page){
        final List<ApplicationDto> applicationDtos =
                applicationService.getMyApplicationsFiltered(id,page, ApplicationState.valueOf(state))
                        .stream().map(application -> ApplicationDto.fromApplication(uriInfo,application))
                        .collect(Collectors.toList());
        if(applicationDtos.isEmpty())
            return Response.noContent().build();
        Response.ResponseBuilder responseBuilder = Response.ok(new GenericEntity<List<ApplicationDto>>(applicationDtos){});

        int lastPage = applicationService.getTotalUserApplicationsFiltered(id,ApplicationState.valueOf(state));
        PaginationLinkBuilder.getResponsePaginationLinks(responseBuilder, uriInfo, page, lastPage);
        return responseBuilder.build();
    }

    @GET
    @Path("/{id}/social-media")
    @Produces("application/vnd.bandify.api.v1+json")
    public Response getUserSocialMedia(@PathParam("id") final long id){
        final Set<SocialMediaDto> socialMediaDtos = userService.getUserById(id)
                .orElseThrow(UserNotFoundException::new).getSocialSocialMedia()
                .stream().map(socialMedia -> SocialMediaDto.fromSocialMedia(uriInfo,socialMedia))
                .collect(Collectors.toSet());
        if(socialMediaDtos.isEmpty())
            return Response.noContent().build();
        Response.ResponseBuilder responseBuilder =
                Response.ok(new GenericEntity<Set<SocialMediaDto>>(socialMediaDtos){});
        return responseBuilder.build();
    }

    @GET
    @Path("/{userId}/social-media/{id}")
    @Produces("application/vnd.bandify.api.v1+json")
    public Response getSocialMedia(@PathParam("userId") final long userId,
                                   @PathParam("id") final long id){
        final Set<SocialMedia> socialMedia = userService.getUserById(userId).orElseThrow(UserNotFoundException::new)
                .getSocialSocialMedia().stream().filter(socialMedia1 -> socialMedia1.getId().equals(id))
                .collect(Collectors.toSet());
        return Response.ok(SocialMediaDto.fromSocialMedia(
                uriInfo,socialMedia.stream().findFirst().orElseThrow(
                        SocialMediaNotFoundException::new))).build();
    }

}