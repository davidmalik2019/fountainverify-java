package com.fountainverify.fountainverify.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fountainverify.fountainverify.entity.Certificate;
import com.fountainverify.fountainverify.repository.CertificateRepository;

@Controller
public class VerificationController {

    private final CertificateRepository certificateRepository;

    public VerificationController(
            CertificateRepository certificateRepository) {

        this.certificateRepository = certificateRepository;
    }

    @GetMapping("/verify")
    public String verifyCertificate(
            @RequestParam(
                    value = "certificateNumber",
                    required = false
            )
            String certificateNumber,
            Model model) {

        if (certificateNumber == null ||
                certificateNumber.trim().isEmpty()) {

            return "verification/form";
        }

        Certificate certificate =
                certificateRepository.findByCertificateNumber(
                        certificateNumber.trim()
                );

        if (certificate == null) {

            model.addAttribute(
                    "message",
                    "Certificate not found. The certificate number is invalid."
            );

            model.addAttribute(
                    "verificationStatus",
                    "INVALID"
            );

            return "verification/result";
        }

        model.addAttribute("certificate", certificate);

        // Check blocked status.
        if ("BLOCKED".equalsIgnoreCase(certificate.getStatus())) {

            model.addAttribute(
                    "message",
                    "This certificate has been blocked and is no longer valid."
            );

            model.addAttribute("verificationStatus", "BLOCKED");

            return "verification/result";
        }

        // Check revoked status.
        if ("REVOKED".equalsIgnoreCase(certificate.getStatus())) {

            model.addAttribute(
                    "message",
                    "This certificate has been revoked and is no longer valid."
            );

            model.addAttribute("verificationStatus", "REVOKED");

            return "verification/result";
        }

        // Check expiry date.
        if (certificate.getExpiryDate() != null &&
                certificate.getExpiryDate().isBefore(LocalDate.now())) {

            model.addAttribute(
                    "message",
                    "This certificate has expired."
            );

            model.addAttribute("verificationStatus", "EXPIRED");

            return "verification/result";
        }

        // Check active status.
        if (!"ACTIVE".equalsIgnoreCase(certificate.getStatus())) {

            model.addAttribute(
                    "message",
                    "This certificate is not currently active."
            );

            model.addAttribute("verificationStatus", "INVALID");

            return "verification/result";
        }

        // Certificate is valid.
        model.addAttribute(
                "message",
                "Certificate verified successfully."
        );

        model.addAttribute("verificationStatus", "VALID");

        return "verification/result";
    }
}