package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.ApplicationState;
import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.SocialMedia;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.SocialMediaNotFoundException;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.ApplicationService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.controller.utils.ConditionalCacheUtil;
import ar.edu.itba.paw.webapp.controller.utils.PaginationLinkBuilder;
import ar.edu.itba.paw.webapp.dto.ApplicationDto;
import ar.edu.itba.paw.webapp.dto.SocialMediaDto;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.*;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;

@Path("users")
@Component
public class UserController {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private UserService userService;

    @Autowired
    private ValidatorFactory validatorFactory;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes("application/vnd.user.v1+json")
    public Response createUser(@Valid UserForm form) {
        User.UserBuilder builder = new User.UserBuilder(form.getEmail(), form.getPassword(),
                form.getName(), form.getBand(), false).surname(form.getSurname());
        final User user = userService.create(builder);
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(user.getId())).build();
        return Response.created(uri).build();
    }

    // TODO: si soy banda tendria que aceptarme no mandar apellido ni available, pero
    // TODO: en el caso de ser artista deberia pedirme los 4 atributos (name,surname,description,available)
    // TODO: mas la lista de generos roles y location
    @PUT
    @Path("/{id}")
    public Response updateUser(@Valid UserEditForm form, @PathParam("id") final long id) {
        userService.editUser(id, form.getName(), form.getSurname(), form.getDescription(), form.getAvailable(),
                form.getRoles(), form.getGenres(), form.getLocation());
        return Response.ok().build();
    }

    @GET
    @Path("/{id}")
    @Produces("application/vnd.user.v1+json")
    public Response getById(@PathParam("id") final long id) {
        final User user = userService.getUserById(id).orElseThrow(UserNotFoundException::new);
        return Response.ok(UserDto.fromUser(uriInfo, user)).build();
    }

    @GET
    @Path("/{id}/profile-image")
    @Produces(value = {"image/webp"})
    public Response getUserProfileImage(@PathParam("id") final long id, @Context Request request) throws IOException {
        EntityTag eTag = new EntityTag(String.valueOf(id));
        Response.ResponseBuilder response = ConditionalCacheUtil.getConditionalCache(request, eTag);
        if(response == null)
            return Response.ok(new ByteArrayInputStream(userService.getProfilePicture(id))).tag(eTag).build();
        else
            return response.build();
    }

    @PUT
    @Path("/{id}/profile-image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateUserProfileImage(@PathParam("id") final long id,
                                           @FormDataParam("image") InputStream image,
                                           @FormDataParam("image") FormDataContentDisposition imageMetaData) throws IOException {
        //TODO file type y size
        userService.updateProfilePicture(
                userService.getUserById(id).orElseThrow(UserNotFoundException::new),
                IOUtils.toByteArray(image));
        return Response.ok().build();
    }

    @GET
    @Produces("application/vnd.user-list.v1+json")
    public Response usersSearch(@QueryParam("page") @DefaultValue("1") final int page,
                                @QueryParam("query") @DefaultValue("") final String query,
                                @QueryParam("genre") final List<String> genres,
                                @QueryParam("role") final List<String> roles,
                                @QueryParam("location")  final List<String> locations,
                                @QueryParam("email") final String email
                                ) {
        if(email != null && !email.isEmpty()) {
            final User user = userService.findByEmail(email).orElseThrow(UserNotFoundException::new);
            return Response.ok(new GenericEntity<List<UserDto>>(Collections.singletonList( UserDto.fromUser(uriInfo, user))){}).build();
        }
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

    //TODO REVISAR SI SE PUEDE PASAR EL CHECK OWNERSHIP AL SERVICIO
    // TODO: las aplicaciones que da son por defecto las pendientes
    // le podes pasar para que te de las del estado que quieras, podemos dejarlo asi
    // o que por defecto te de las que sean de cualquier estado.
    @GET
    @Path("/{id}/applications")
    @Produces("application/vnd.application-list.v1+json")
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

        int lastPage = applicationService.getTotalUserApplicationPagesFiltered(id,ApplicationState.valueOf(state));
        PaginationLinkBuilder.getResponsePaginationLinks(responseBuilder, uriInfo, page, lastPage);
        return responseBuilder.build();
    }

    @GET
    @Path("/{id}/social-media")
    @Produces("application/vnd.social-media-list.v1+json")
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

    @PUT
    @Path("/{id}/social-media")
    @Consumes("application/vnd.social-media-list.v1+json")
    public Response updateUserSocialMedia(@Valid SocialMediaForm form, @PathParam("id") final long id) {
        userService.updateSocialMedia(id, form.getSocialMedia());
        return Response.ok().build();
    }

    @GET
    @Path("/{userId}/social-media/{id}")
    @Produces("application/vnd.social-media.v1+json")
    public Response getSocialMedia(@PathParam("userId") final long userId,
                                   @PathParam("id") final long id){
        final Set<SocialMedia> socialMedia = userService.getUserById(userId).orElseThrow(UserNotFoundException::new)
                .getSocialSocialMedia().stream().filter(socialMedia1 -> socialMedia1.getId().equals(id))
                .collect(Collectors.toSet());
        return Response.ok(SocialMediaDto.fromSocialMedia(
                uriInfo,socialMedia.stream().findFirst().orElseThrow(
                        SocialMediaNotFoundException::new))).build();
    }

    @POST
    @Path("/verify-tokens")
    @Consumes("application/vnd.verify-token.v1+json")
    public Response resendUserVerification(@Valid SendTokenToEmailForm form) {
        userService.resendUserVerification(form.getEmail());
        return Response.ok().build();
    }

    @PUT
    @Path("/verify-tokens/{token}")
    @Consumes("application/vnd.verify-token.v1+json")
    public Response verifyUser(@PathParam("token") final String token) {
        userService.verifyUser(token);
        return Response.ok().build();
    }

    @POST
    @Path("/password-tokens")
    @Consumes("application/vnd.password-token.v1+json")
    public Response generateUserPassword(@Valid SendTokenToEmailForm form) {
        userService.sendResetEmail(form.getEmail());
        return Response.ok().build();
    }

    @PUT
    @Path("/password-tokens/{token}")
    @Consumes("application/vnd.password-token.v1+json")
    public Response updateUserPassword(@Valid NewPasswordForm form,
                                     @PathParam("token") final String token) {
        userService.changePassword(token, form.getNewPassword());
        return Response.ok().build();
    }

}