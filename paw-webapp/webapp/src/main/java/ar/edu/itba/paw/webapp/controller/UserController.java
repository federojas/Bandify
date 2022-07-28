package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.UserDto;
import ar.edu.itba.paw.webapp.form.UserArtistForm;
import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.Media;
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
    private UserService us;

    @Context
    private UriInfo uriInfo;

    @POST
    @Consumes(value = {MediaType.APPLICATION_JSON, })
    public Response createUser(@Valid UserForm form) {
        User.UserBuilder builder = new User.UserBuilder(form.getEmail(), form.getPassword(),
                form.getName(), form.isBand(), false);
        //TODO que hacemos con esto?
//        if(form.isBand())
//            builder.surname(form.getSurname());
        final User user = us.create(builder);
        final URI uri = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(user.getId())).build();
        return Response.created(uri).build();
    }


    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("id") final long id) {
        final Optional<User> user = us.getUserById(id);
        if (user.isPresent())
            return Response.ok(UserDto.fromUser(uriInfo, user.get())).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}/profile-image")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getUserProfileImage(@PathParam("id") Long id) throws IOException {
        return Response.ok(new ByteArrayInputStream(us.getProfilePicture(id))).build();
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
        List<UserDto> users = us.filter(filter, page)
                .stream().map(u -> UserDto.fromUser(uriInfo, u)).collect(Collectors.toList());;
        if(users.isEmpty()) {
            return Response.noContent().build();
        }
        Response.ResponseBuilder response = Response.ok(new GenericEntity<List<UserDto>>(users) {});
        getResponsePaginationLinks(response, page, filter);
        return response.build();
    }

    private void getResponsePaginationLinks(Response.ResponseBuilder response, int currentPage, FilterOptions filter) {
        int lastPage = us.getFilterTotalPages(filter);
        if(currentPage != 1)
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", currentPage - 1).build(), "prev");
        if(currentPage != lastPage)
            response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", currentPage + 1).build(), "next");
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", 1).build(), "first");
        response.link(uriInfo.getAbsolutePathBuilder().queryParam("page", lastPage).build(), "last");
    }
}