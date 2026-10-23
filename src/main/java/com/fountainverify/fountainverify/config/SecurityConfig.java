
package com.fountainverify.fountainverify.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http
        

            .authorizeHttpRequests(auth -> auth

                // Public pages and resources
                .requestMatchers(
                    "/register",
                    "/login",
                    "/auth/register",
                    "/qrcode",
                    "/verify",
                    "/images/**"
                ).permitAll()

                // Certificate management: administrators only
                .requestMatchers("/certificates/**")
                .hasRole("ADMIN")

                // Personal pages: login required
                .requestMatchers(
                    "/dashboard",
                    "/my-certificates",
                    "/my-qr-codes",
                    "/my-certificates/**"
                ).authenticated()

                // All other routes require authentication
                .anyRequest().authenticated()
            )

            // Configure login and logout behavior
            .formLogin(form -> form
            	    .loginPage("/login")
            	    .defaultSuccessUrl("/dashboard", true)
            	    .permitAll()
            	)

            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(
                    (request, response, exception) ->
                        response.sendRedirect("/login")
                )
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );

        return http.build();
    }
}
