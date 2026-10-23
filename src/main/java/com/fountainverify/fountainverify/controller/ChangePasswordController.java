
package com.fountainverify.fountainverify.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fountainverify.fountainverify.entity.User;
import com.fountainverify.fountainverify.repository.UserRepository;

@Controller
public class ChangePasswordController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/change-password")
    public String showChangePasswordPage(
            Authentication authentication,
            Model model) {

        String username = authentication.getName();

        User user =
                userRepository.findByUsername(username)
                        .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("user", user);

        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(
            Authentication authentication,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword,
            @RequestParam("confirmPassword") String confirmPassword,
            Model model) {

        String username = authentication.getName();

        User user =
                userRepository.findByUsername(username)
                        .orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        /*
         * Check current password.
         */
        if (!passwordEncoder.matches(
                currentPassword,
                user.getPassword())) {

            model.addAttribute(
                    "error",
                    "Current password is incorrect."
            );

            model.addAttribute("user", user);

            return "change-password";
        }

        /*
         * Check that the new passwords match.
         */
        if (!newPassword.equals(confirmPassword)) {

            model.addAttribute(
                    "error",
                    "New password and confirmation password do not match."
            );

            model.addAttribute("user", user);

            return "change-password";
        }

        /*
         * Prevent using the same password.
         */
        if (passwordEncoder.matches(
                newPassword,
                user.getPassword())) {

            model.addAttribute(
                    "error",
                    "Your new password must be different from your current password."
            );

            model.addAttribute("user", user);

            return "change-password";
        }

        /*
         * Encrypt and save the new password.
         */
        user.setPassword(
                passwordEncoder.encode(newPassword)
        );

        userRepository.save(user);

        model.addAttribute(
                "success",
                "Password changed successfully."
        );

        model.addAttribute("user", user);

        return "change-password";
    }
}

