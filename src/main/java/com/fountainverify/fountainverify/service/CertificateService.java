
package com.fountainverify.fountainverify.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fountainverify.fountainverify.entity.Certificate;
import com.fountainverify.fountainverify.entity.User;
import com.fountainverify.fountainverify.repository.CertificateRepository;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final QRCodeService qrCodeService;

    @Value("${fountainverify.base-url}")
    private String baseUrl;

    public CertificateService(
            CertificateRepository certificateRepository,
            QRCodeService qrCodeService) {

        this.certificateRepository = certificateRepository;
        this.qrCodeService = qrCodeService;
    }

    /*
     * Create a new certificate and generate its QR code.
     */
    public Certificate createCertificate(
            String certificateNumber,
            String recipientName,
            String courseName,
            String issuerName,
            LocalDate issueDate,
            LocalDate expiryDate,
            User user) {

        if (certificateRepository.existsByCertificateNumber(
                certificateNumber)) {
            throw new RuntimeException(
                    "Certificate number already exists.");
        }

        Certificate certificate = new Certificate();

        certificate.setCertificateNumber(certificateNumber);
        certificate.setRecipientName(recipientName);
        certificate.setCourseName(courseName);
        certificate.setIssuerName(issuerName);
        certificate.setIssueDate(issueDate);
        certificate.setExpiryDate(expiryDate);
        certificate.setStatus("ACTIVE");
        certificate.setCreatedAt(LocalDateTime.now());
        certificate.setUser(user);

        try {
            String verificationUrl =
                    baseUrl
                    + "/verify?certificateNumber="
                    + certificateNumber;

            byte[] qrBytes = qrCodeService.generateQRCode(
                    verificationUrl, 300, 300);

            String qrCodeBase64 = Base64.getEncoder()
                    .encodeToString(qrBytes);

            certificate.setQrCode(qrCodeBase64);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to generate QR code.", e);
        }

        return certificateRepository.save(certificate);
    }

    /*
     * Get all certificates.
     */
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    /*
     * Get a certificate by ID.
     */
    public Certificate getCertificateById(Long id) {
        return certificateRepository.findById(id).orElse(null);
    }

    /*
     * Regenerate the QR code for an existing certificate.
     */
    public Certificate regenerateQRCode(Long id) {

        Certificate certificate =
                certificateRepository.findById(id).orElse(null);

        if (certificate == null) {
            return null;
        }

        try {
            String verificationUrl =
                    baseUrl
                    + "/verify?certificateNumber="
                    + certificate.getCertificateNumber();

            byte[] qrBytes = qrCodeService.generateQRCode(
                    verificationUrl, 300, 300);

            String qrCodeBase64 = Base64.getEncoder()
                    .encodeToString(qrBytes);

            certificate.setQrCode(qrCodeBase64);

            return certificateRepository.save(certificate);

        } catch (Exception e) {
            throw new RuntimeException(
                    "Unable to regenerate QR code.", e);
        }
    }

    /*
     * Update certificate details and assign it to a user account.
     */
    public Certificate updateCertificate(
            Long id,
            String certificateNumber,
            String recipientName,
            String courseName,
            String issuerName,
            LocalDate issueDate,
            LocalDate expiryDate,
            String status,
            User user) {

        Certificate certificate =
                certificateRepository.findById(id).orElse(null);

        if (certificate == null) {
            return null;
        }

        certificate.setCertificateNumber(certificateNumber);
        certificate.setRecipientName(recipientName);
        certificate.setCourseName(courseName);
        certificate.setIssuerName(issuerName);
        certificate.setIssueDate(issueDate);
        certificate.setExpiryDate(expiryDate);
        certificate.setStatus(status);
        certificate.setUser(user);

        return certificateRepository.save(certificate);
    }

    /*
     * Delete an existing certificate.
     */
    public void deleteCertificate(Long id) {

        Certificate certificate =
                certificateRepository.findById(id).orElse(null);

        if (certificate == null) {
            return;
        }

        certificateRepository.delete(certificate);
    }

    /*
     * Get certificates belonging to a specific user.
     */
    public List<Certificate> getCertificatesByUser(User user) {
        return certificateRepository.findByUser(user);
    }
}
