package com.gaonkar.anuj.bookstore.controller;

import com.gaonkar.anuj.bookstore.model.User;
import com.gaonkar.anuj.bookstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
@Slf4j
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    public Iterable<User> getAllUsers(){
        return this.userRepository.findAll();
    }
}
