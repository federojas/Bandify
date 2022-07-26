package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.model.FilterOptions;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("users")
@Component
public class UserController {
    @Autowired
    private UserService us;
//    @Context
//    private UriInfo uriInfo;

    @GET
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response listUsers(@QueryParam("page") @DefaultValue("1") final int page) {
        final List<User> allUsers = us.filter(new FilterOptions.FilterOptionsBuilder().withTitle("").withOrder("").withGenres(null).withLocations(null).withRoles(null).build(), page);
        if(allUsers.isEmpty()) {
            return Response.noContent().build();
        }
        //TODO links y user dto
//        return Response.ok(new GenericEntity<List<User>>(allUsers) {}).build();
        return Response.ok().build();
    }

    //register
//    @POST
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response createUser(final UserDTO userDto) {
//        final User user = us.register(userDto.getUsername(), userDto.getPassword());
//        final URI uri = uriInfo.getAbsolutePathBuilder()
//                .path(String.valueOf(user.getId())).build();
//        return Response.created(uri).build();
//    }
//
//    @GET
//    @Path("/{id}")
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response getById(@PathParam("id") final long id) {
//        final Optional<User> user = us.getUserById(id);
//        if (!user.isPresent()) {
//            return Response.ok(new UserDTO(user)).build();
//        } else {
//            return Response.status(Response.Status.NOT_FOUND).build();
//        }
//    }

    //NO HAY DELETE
//    @DELETE
//    @Path("/{id}")
//    @Produces(value = { MediaType.APPLICATION_JSON, })
//    public Response deleteById(@PathParam("id") final long id) {
//        us.deleteById(id);
//        return Response.noContent().build();
//    }
}