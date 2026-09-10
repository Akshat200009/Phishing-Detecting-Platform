package com.phishing.urlAnalysis.repository;

import com.phishing.Entities.User;
import com.phishing.urlAnalysis.model.UrlScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlScanRepository extends JpaRepository<UrlScan, Long> {

    List<UrlScan> findAllByOrderByScannedAtDesc();
    
    Optional<UrlScan> findByIdAndUser(Long id, User user);

    List<UrlScan> findByUserOrderByScannedAtDesc(User user);

    long countByUser(User user);

    long countByUserAndStatus(User user, String status);
}