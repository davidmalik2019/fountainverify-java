
package com.fountainverify.fountainverify.controller;

import java.time.LocalDate;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fountainverify.fountainverify.entity.Certificate;
import com.fountainverify.fountainverify.entity.User;
import com.fountainverify.fountainverify.service.CertificateService;
import com.fountainverify.fountainverify.service.UserService;

@Controller
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/certificates")
public class CertificateController {

    private final CertificateService certificateService;
    private final UserService userService;

    public CertificateController(
            CertificateService certificateService,
            UserService userService) {

        this.certificateService = certificateService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String showCreateCertificatePage(Model model) {

        model.addAttribute("certificate", new Certificate());
        model.addAttribute("users", userService.getAllUsers());

        return "certificates/create";
    }

    @PostMapping("/create")
    public String createCertificate(
            @RequestParam("certificateNumber") String certificateNumber,
            @RequestParam("recipientName") String recipientName,
            @RequestParam("courseName") String courseName,
            @RequestParam("issuerName") String issuerName,
            @RequestParam("issueDate") LocalDate issueDate,
            @RequestParam(value = "expiryDate", required = false)
                    LocalDate expiryDate,
            @RequestParam("userId") Long userId) {

        User user = userService.getUserById(userId);

        if (user == null) {
            return "redirect:/certificates/create";
        }

        certificateService.createCertificate(
                certificateNumber,
                recipientName,
                courseName,
                issuerName,
                issueDate,
                expiryDate,
                user
        );

        return "redirect:/certificates/list";
    }

    @GetMapping("/list")
    public String listCertificates(Model model) {

        model.addAttribute(
                "certificates",
                certificateService.getAllCertificates()
        );

        return "certificates/list";
    }

    @GetMapping("/view/{id}")
    public String viewCertificate(
            @PathVariable("id") Long id,
            Model model) {

        Certificate certificate =
                certificateService.getCertificateById(id);

        if (certificate == null) {
            return "redirect:/certificates/list";
        }

        model.addAttribute("certificate", certificate);

        return "certificates/view";
    }

    /*
     * Show Edit Certificate page.
     */
    @GetMapping("/edit/{id}")
    public String showEditCertificatePage(
            @PathVariable("id") Long id,
            Model model) {

        Certificate certificate =
                certificateService.getCertificateById(id);

        if (certificate == null) {
            return "redirect:/certificates/list";
        }

        model.addAttribute("certificate", certificate);
        model.addAttribute("users", userService.getAllUsers());

        return "certificates/edit";
    }

    /*
     * Save edited certificate and its assigned user account.
     */
    @PostMapping("/edit/{id}")
    public String updateCertificate(
            @PathVariable("id") Long id,
            @RequestParam("certificateNumber") String certificateNumber,
            @RequestParam("recipientName") String recipientName,
            @RequestParam("courseName") String courseName,
            @RequestParam("issuerName") String issuerName,
            @RequestParam("issueDate") LocalDate issueDate,
            @RequestParam(value = "expiryDate", required = false)
                    LocalDate expiryDate,
            @RequestParam("status") String status,
            @RequestParam("userId") Long userId) {

        User user = userService.getUserById(userId);

        if (user == null) {
            return "redirect:/certificates/edit/" + id;
        }

        certificateService.updateCertificate(
                id,
                certificateNumber,
                recipientName,
                courseName,
                issuerName,
                issueDate,
                expiryDate,
                status,
                user
        );

        return "redirect:/certificates/view/" + id;
    }

    /*
     * Regenerate the QR code for an existing certificate.
     */
    @PostMapping("/regenerate-qr/{id}")
    public String regenerateQRCode(
            @PathVariable("id") Long id) {

        Certificate certificate =
                certificateService.regenerateQRCode(id);

        if (certificate == null) {
            return "redirect:/certificates/list";
        }

        return "redirect:/certificates/view/" + id;
    }

    /*
     * Delete an existing certificate.
     */
    @PostMapping("/delete/{id}")
    public String deleteCertificate(
            @PathVariable("id") Long id) {

        certificateService.deleteCertificate(id);

        return "redirect:/certificates/list";
    }
}
