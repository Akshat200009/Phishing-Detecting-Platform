package com.phishing.urlAnalysis.repository;

import com.phishing.urlAnalysis.model.UrlScan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UrlScanRepository extends JpaRepository<UrlScan, Long> {
    List<UrlScan> findAllByOrderByScannedAtDesc();
}