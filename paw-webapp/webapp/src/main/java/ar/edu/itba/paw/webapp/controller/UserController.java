package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.ApplicationState;
import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.exceptions.UserNotFoundException;
import ar.edu.itba.paw.service.ApplicationService;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.ApplicationDto;
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
import java.util.Optional;
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
    @Consumes(value = {MediaType.APPLICATION_JSON, })
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
    @Consumes(value = {MediaType.APPLICATION_JSON, })
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
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("id") final long id) {
        final Optional<User> user = userService.getUserById(id);
        if (user.isPresent())
            return Response.ok(UserDto.fromUser(uriInfo, user.get())).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}/profile-image")
    public Response getUserProfileImage(@PathParam("id") final long id) throws IOException {
        return Response.ok(new ByteArrayInputStream(userService.getProfilePicture(id))).build();
    }


    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
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
        getResponsePaginationLinks(response, page, filter);
        return response.build();
    }

    private void getResponsePaginationLinks(Response.ResponseBuilder response, int currentPage, FilterOptions filter) {
        int lastPage = userService.getFilterTotalPages(filter);
        if(currentPage != 1)
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", currentPage - 1).build(), "prev");
        if(currentPage != lastPage)
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", currentPage + 1).build(), "next");
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first");
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", lastPage).build(), "last");
    }

    // TODO: seguridad
    @GET
    @Path("/{id}/applications")
    @Produces(value = { MediaType.APPLICATION_JSON, })
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
        if(page != 1)
            responseBuilder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page - 1).build(), "prev");
        if(page != lastPage)
            responseBuilder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", page + 1).build(), "next");
        responseBuilder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first");
        responseBuilder.link(uriInfo.getAbsolutePathBuilder().queryParam("page", lastPage).build(), "last");
        return responseBuilder.build();
    }


}