package com.fountainverify.fountainverify.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fountainverify.fountainverify.entity.User;
import com.fountainverify.fountainverify.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final SecurityContextRepository securityContextRepository =
            new HttpSessionSecurityContextRepository();

    public LoginController(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response) {

        User user = userRepository.findByUsername(username).orElse(null);

        if (user != null &&
                passwordEncoder.matches(password, user.getPassword())) {

        	Authentication authentication =
        	        new UsernamePasswordAuthenticationToken(
        	                user.getUsername(),
        	                null,
        	                java.util.Collections.singletonList(
        	                        new org.springframework.security.core.authority.SimpleGrantedAuthority(
        	                                "ROLE_" + user.getRole()
        	                        )
        	                )
        	        );
        	System.out.println(
        	        "LOGIN USER: " + user.getUsername()
        	        + " | ROLE: " + user.getRole()
        	        + " | AUTHORITIES: " + authentication.getAuthorities()
        	);
            SecurityContext securityContext =
                    SecurityContextHolder.createEmptyContext();

            securityContext.setAuthentication(authentication);

            SecurityContextHolder.setContext(securityContext);

            securityContextRepository.saveContext(
                    securityContext,
                    request,
                    response
            );

            return "redirect:/dashboard";

        } else {

            model.addAttribute(
                    "error",
                    "Invalid username or password."
            );
        }

        return "login";
    }
    @GetMapping("/reset-testuser")
    public String resetTestUserPassword() {

        User user = userRepository
                .findByUsername("testuser")
                .orElse(null);

        if (user != null) {
            user.setPassword(
                    passwordEncoder.encode("Test1234")
            );

            userRepository.save(user);
        }

        return "redirect:/login";
    }
}