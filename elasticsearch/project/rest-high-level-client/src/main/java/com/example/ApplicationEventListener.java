package com.example;

import static java.util.stream.Collectors.toList;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.model.Company;
import com.example.model.User;
import com.example.service.CompanyService;
import com.example.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ApplicationEventListener {
    private final SecureRandom random = new SecureRandom();
    private final CompanyService companyService;
    private final UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void readyEvent() {
        List<Company> companies = IntStream.rangeClosed(1, 100)
                                           .mapToObj(this::createCompany)
                                           .collect(toList());
        companyService.bulkIndex(companies);

        List<User> users = IntStream.rangeClosed(1, 100)
                                    .mapToObj(this::createUser)
                                    .collect(toList());
        userService.bulkIndex(users);
    }

    private Company createCompany(int id) {
        String name = "company" + id;
        String address = "address" + id + ' ' + id / 10;
        double score = random.nextDouble();
        return new Company(id, name, address, score);
    }

    private User createUser(int id) {
        String name = "user" + id;
        String email = name + "@example.com";
        double score = random.nextDouble();
        return new User(id, name, email, score);
    }
}
