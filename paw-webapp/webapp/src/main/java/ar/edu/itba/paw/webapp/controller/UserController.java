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
import ar.edu.itba.paw.webapp.dto.UserStatusDto;
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
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
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

    @Autowired
    private ValidatorFactory validatorFactory;

    @Context
    private UriInfo uriInfo;

    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes("application/vnd.user.v1+json")
    public Response createUser(UserForm form,
                               @QueryParam("email") final String email) {
        if(email != null) {
            userService.resendUserVerification(email);
            return Response.ok().build();
        }
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<UserForm>> violations = validator.validate(form);
        if(!violations.isEmpty())
            throw new ConstraintViolationException(violations);
        User.UserBuilder builder = new User.UserBuilder(form.getEmail(), form.getPassword(),
                form.getName(), form.getBand(), false).surname(form.getSurname());
        final User user = userService.create(builder);
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(user.getId())).build();
        return Response.created(uri).build();
    }

    //TODO REVISAR SI SE PUEDE PASAR EL CHECK OWNERSHIP AL SERVICIO
    // TODO: si soy banda tendria que aceptarme no mandar apellido ni available, pero
    // TODO: en el caso de ser artista deberia pedirme los 4 atributos (name,surname,description,available)
    // TODO: mas la lista de generos roles y location
    @PUT
    @Path("/{id}")
    public Response updateUser(@Valid UserEditForm form, @PathParam("id") final long id) {
        final User user = userService.findByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        checkOwnership(user, id);
        userService.editUser(user.getId(), form.getName(), form.getSurname(), form.getDescription(), form.getAvailable(),
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
    @Produces({MediaType.MULTIPART_FORM_DATA})
    public Response getUserProfileImage(@PathParam("id") final long id) throws IOException {
        return Response.ok(Base64.getEncoder().encodeToString(userService.getProfilePicture(id))).build();
    }

    @PUT
    @Path("/{id}/profile-image")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response updateUserProfileImage(@PathParam("id") final long id,
                                           @FormDataParam("image") InputStream image, //TODO IMAGE TYPE Y SIZE
                                           @FormDataParam("image")FormDataContentDisposition imageMetaData) throws IOException {
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

        final User user = userService.findByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        checkOwnership(user, id);
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
        final User user = userService.findByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        checkOwnership(user, id);
        userService.updateSocialMedia(user, form.getSocialMedia());
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

    @GET
    @Path("/{id}/status")
    @Produces("application/vnd.status.v1+json")
    public Response getUserStatus(@PathParam("id") final long id) {
        final User user = userService.findByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        checkOwnership(user, id);
        return Response.ok(UserStatusDto.fromUser(uriInfo, user.isEnabled(),id)).build();
    }

    //  TODO: Al form le puse un enum validator para estar seguros de que mandaron
    //  TODO: un enum valido, en este caso solo esta NOT_VERIFIED y VERIFIED
    //  TODO: pero en realidad nunca estoy revisando cual status mandaron
    //  TODO: quizas podriamos cambiar el metodo verifyUser de userService por updateUserStatus

    @PUT
    @Path("/{id}/status")
    @Consumes("application/vnd.status.v1+json")
    public Response updateUserStatus(@Valid UserStatusForm form,
                                     @PathParam("id") final long id,
                                     @HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) {
        String[] payload = authHeader.split(" ");
        final User user = userService.findByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        checkOwnership(user, id);
        String token = new String(Base64.getDecoder().decode(payload[1].trim()), StandardCharsets.UTF_8).split(":")[1];
        userService.verifyUser(token);
        return Response.ok().build();
    }

    @POST
    @Path("/{id}/password")
    @Consumes("application/vnd.password.v1+json")
    public Response generateUserPassword(@Valid SendTokenToEmailForm form,
                                     @PathParam("id") final long id) {
        final User user = userService.findByEmail(form.getEmail()).orElseThrow(UserNotFoundException::new);
        checkOwnership(user, id);
        userService.sendResetEmail(user.getEmail()); //TODO aca por ahi mejor pasar el user directo? pasa que los services deberian validar
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}/password")
    @Consumes("application/vnd.password.v1+json")
    public Response updateUserPassword(@Valid NewPasswordForm form,
                                     @PathParam("id") final long id,
                                     @HeaderParam(HttpHeaders.AUTHORIZATION) String authHeader) {
        String[] payload = authHeader.split(" ");
        final User user = userService.findByEmail(securityContext.getUserPrincipal().getName()).orElseThrow(UserNotFoundException::new);
        checkOwnership(user, id);
        String token = new String(Base64.getDecoder().decode(payload[1].trim()), StandardCharsets.UTF_8).split(":")[1];
        userService.changePassword(token, form.getNewPassword());
        return Response.ok().build();
    }


    private void checkOwnership(User user, long userId) {
        if (user.getId() != userId) {
            throw new ForbiddenException();
        }
    }

}