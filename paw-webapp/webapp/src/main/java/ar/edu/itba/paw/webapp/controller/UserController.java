package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import ar.edu.itba.paw.webapp.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.soap.SOAPBinding;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Filter;
import java.util.stream.Collectors;

@Path("users")
@Component
public class UserController {

    @Autowired
    private UserService us;

    @Context
    private UriInfo uriInfo;

    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listUsers(@QueryParam("page") @DefaultValue("1") final int page) {
        final List<UserDto> allUsers = us.filter(new FilterOptions.FilterOptionsBuilder().withTitle("").withOrder("").withGenres(null).withLocations(null).withRoles(null).build(), page)
                .stream().map(u -> UserDto.fromUser(uriInfo, u)).collect(Collectors.toList());
        if(allUsers.isEmpty()) {
            return Response.noContent().build();
        }


        //TODO links MODULARIZAR PAGINATION LINKS
        return Response.ok(new GenericEntity<List<UserDto>>(allUsers) {}).build();
    }

    //register TODO FORM DTO
//    @POST
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response createUser(final UserDTO userDto) {
//        final User user = us.register(userDto.getUsername(), userDto.getPassword());
//        final URI uri = uriInfo.getAbsolutePathBuilder()
//                .path(String.valueOf(user.getId())).build();
//        return Response.created(uri).build();
//    }
//
    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("id") final long id) {
        final Optional<User> user = us.getUserById(id);
        //TODO links
        if (user.isPresent())
            return Response.ok(UserDto.fromUser(uriInfo, user.get())).build();
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Path("/{id}/profile-image")
    @Produces("application/vnd.campus.api.v1+json")
    public Response getUserProfileImage(@PathParam("id") Long id) throws IOException {
        //TODO links
        return Response.ok(new ByteArrayInputStream(us.getProfilePicture(id))).build();
    }

    //TODO SEGUN LEI EL DEFAULT ES QUE LOS QUERY PARAM NO SON NUNCA OBLIGATORIOS, MANDAN NULL
    @GET
    @Path("/search")
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
        //TODO links
        return Response.ok(new GenericEntity<List<UserDto>>(users) {}).build();
    }


    //TODO NO HAY DELETE JERSEY MANDA 405 AUTOMATICAMENTE O HACERLO
//    @DELETE
//    @Path("/{id}")
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response deleteById(@PathParam("id") final long id) {
//        us.deleteById(id);
//        return Response.noContent().build();
//    }
}