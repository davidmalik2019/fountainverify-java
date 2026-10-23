package com.fountainverify.fountainverify.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fountainverify.fountainverify.entity.Certificate;
import com.fountainverify.fountainverify.entity.User;
import com.fountainverify.fountainverify.service.CertificateService;
import com.fountainverify.fountainverify.service.UserService;

@Controller
public class MyCertificatesController {

    private final CertificateService certificateService;
    private final UserService userService;

    public MyCertificatesController(
            CertificateService certificateService,
            UserService userService) {

        this.certificateService = certificateService;
        this.userService = userService;
    }

    @GetMapping("/my-certificates")
    public String myCertificates(
            Authentication authentication,
            Model model) {

        String username = authentication.getName();

        User user = userService.getUserByUsername(username);

        if (user == null) {
            return "redirect:/login";
        }

        List<Certificate> certificates =
                certificateService.getCertificatesByUser(user);

        model.addAttribute("certificates", certificates);
        model.addAttribute("username", user.getUsername());

        return "certificates/my-certificates";
    }
}