package com.phishing.emailAnalysis.repository;

import com.phishing.Entities.User;
import com.phishing.emailAnalysis.model.EmailScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailScanRepository extends JpaRepository<EmailScan, Long> {

    List<EmailScan> findAllByUserOrderByScannedAtDesc(User user);
    Optional<EmailScan> findByIdAndUser(Long id, User user);
}