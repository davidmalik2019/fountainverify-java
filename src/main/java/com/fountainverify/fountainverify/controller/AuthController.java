
package com.fountainverify.fountainverify.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fountainverify.fountainverify.dto.RegisterRequest;
import com.fountainverify.fountainverify.entity.User;
import com.fountainverify.fountainverify.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(
            @RequestBody RegisterRequest request) {

        System.out.println(">>> AUTH REGISTER ENDPOINT REACHED <<<");

        User user = userService.registerUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok(
                "Registration successful for username: "
                        + user.getUsername()
        );
    }
}

