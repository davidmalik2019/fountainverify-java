package com.fountainverify.fountainverify.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fountainverify.fountainverify.service.UserService;

@RestController
public class PasswordResetController {

    private final UserService userService;

    public PasswordResetController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/reset-testuser-password")
    public String resetTestUserPassword() {

        userService.resetPassword("testuser", "Test1234");

        return "testuser password reset successfully";
    }
}