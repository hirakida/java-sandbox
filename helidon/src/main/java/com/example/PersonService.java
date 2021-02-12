package com.example;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import io.helidon.webserver.Routing;
import io.helidon.webserver.ServerRequest;
import io.helidon.webserver.ServerResponse;
import io.helidon.webserver.Service;

public class PersonService implements Service {

    @Override
    public void update(Routing.Rules rules) {
        rules.get("/", PersonService::getPersons)
             .get("/{id}", PersonService::getPerson);
    }

    private static void getPersons(ServerRequest request, ServerResponse response) {
        List<Person> persons = IntStream.rangeClosed(1, 10)
                                        .mapToObj(i -> new Person(i, "name" + i))
                                        .collect(Collectors.toList());
        response.send(persons);
    }

    private static void getPerson(ServerRequest request, ServerResponse response) {
        int id = Integer.parseInt(request.path().param("id"));
        response.send(new Person(id, "name" + id));
    }
}
