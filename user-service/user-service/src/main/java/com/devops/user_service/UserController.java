package com.devops.user_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public List<User> getUsers() {

        return List.of(
                new User(1L, "Rakesh"),
                new User(2L, "Amit"),
                new User(3L, "Rahul")
        );
    }

    record User(Long id, String name) {
    }
}
