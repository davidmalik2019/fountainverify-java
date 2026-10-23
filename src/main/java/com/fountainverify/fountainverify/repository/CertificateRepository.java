package com.fountainverify.fountainverify.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fountainverify.fountainverify.entity.Certificate;
import com.fountainverify.fountainverify.entity.User;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    boolean existsByCertificateNumber(String certificateNumber);

    Certificate findByCertificateNumber(String certificateNumber);

    /*
     * Get all certificates belonging to a specific user.
     */
    List<Certificate> findByUser(User user);
}