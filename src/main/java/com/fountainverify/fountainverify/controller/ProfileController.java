
package com.fountainverify.fountainverify.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fountainverify.fountainverify.entity.User;
import com.fountainverify.fountainverify.service.UserService;

@Controller
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public String profile(
            Authentication authentication,
            Model model) {

        String username = authentication.getName();

        User user = userService.getUserByUsername(username);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "profile";
    }
}

