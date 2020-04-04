package com.stepanov.documentclient.controller;

import com.stepanov.documentclient.model.User;
import com.stepanov.documentclient.service.UserServiceImpl;
import com.stepanov.documentclient.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private UserServiceImpl userService;
    private UserValidator userDtoValidator;

    @Autowired
    public UserController(UserServiceImpl userService, UserValidator userDtoValidator) {
        this.userService = userService;
        this.userDtoValidator = userDtoValidator;
    }

    @GetMapping("/registration")
    public String getUserFormRegistration(User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(@Valid User user, BindingResult result) {
        userDtoValidator.validate(user, result);
        if (result.hasErrors()) {
            return "registration";
        }
        userService.saveUser(user);
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "login";
    }
}
