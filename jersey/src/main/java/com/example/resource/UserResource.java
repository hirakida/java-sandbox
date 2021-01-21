package com.example.resource;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.model.User;

@Path("/users")
public class UserResource {
    private final List<User> users;

    public UserResource() {
        users = IntStream.rangeClosed(1, 5)
                         .mapToObj(i -> new User(i, "name" + i))
                         .collect(Collectors.toList());
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> findAll() {
        return users;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public User findById(@PathParam("id") int id) {
        return users.stream()
                    .filter(user -> user.getId() == id)
                    .findFirst()
                    .orElseThrow();
    }
}
