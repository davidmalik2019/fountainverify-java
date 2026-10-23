package com.fountainverify.fountainverify.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fountainverify.fountainverify.entity.Certificate;
import com.fountainverify.fountainverify.entity.User;
import com.fountainverify.fountainverify.service.CertificateService;
import com.fountainverify.fountainverify.service.UserService;

@Controller
public class MyCertificateViewController {

    private final CertificateService certificateService;
    private final UserService userService;

    public MyCertificateViewController(
            CertificateService certificateService,
            UserService userService) {

        this.certificateService = certificateService;
        this.userService = userService;
    }

    @GetMapping("/my-certificates/view/{id}")
    public String viewMyCertificate(
            @PathVariable("id") Long id,
            Authentication authentication,
            Model model) {

        String username = authentication.getName();

        User user = userService.getUserByUsername(username);

        if (user == null) {
            return "redirect:/login";
        }

        Certificate certificate =
                certificateService.getCertificateById(id);

        if (certificate == null) {
            return "redirect:/my-certificates";
        }

        if (certificate.getUser() == null ||
            !certificate.getUser().getId().equals(user.getId())) {

            return "redirect:/my-certificates";
        }

        model.addAttribute("certificate", certificate);
        model.addAttribute("studentView", true);

        return "certificates/view";
    }
}