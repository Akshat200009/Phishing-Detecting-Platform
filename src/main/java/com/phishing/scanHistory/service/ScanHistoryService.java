package com.phishing.scanHistory.service;

import com.phishing.Entities.User;
import com.phishing.emailAnalysis.model.EmailScan;
import com.phishing.emailAnalysis.repository.EmailScanRepository;
import com.phishing.scanHistory.dto.ScanHistoryResponse;
import com.phishing.scanHistory.dto.ScanReportResponse;
import com.phishing.urlAnalysis.model.UrlScan;
import com.phishing.urlAnalysis.repository.UrlScanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@Service
public class ScanHistoryService {

    private final UrlScanRepository urlScanRepository;
    private final EmailScanRepository emailScanRepository;

    public ScanHistoryService(
            UrlScanRepository urlScanRepository,
            EmailScanRepository emailScanRepository) {

        this.urlScanRepository = urlScanRepository;
        this.emailScanRepository = emailScanRepository;
    }

    // =========================
    // Unified History
    // Search + Filters
    // =========================

    public List<ScanHistoryResponse> getAllHistory(
            User currentUser,
            String search,
            String type,
            String status) {

        List<ScanHistoryResponse> history = new ArrayList<>();

        String normalizedSearch =
                search == null
                        ? ""
                        : search.trim().toLowerCase(Locale.ROOT);

        String normalizedType =
                type == null
                        ? ""
                        : type.trim().toUpperCase(Locale.ROOT);

        String normalizedStatus =
                status == null
                        ? ""
                        : status.trim().toUpperCase(Locale.ROOT);

        // =========================
        // URL Scans
        // =========================

        List<UrlScan> urlScans =
                urlScanRepository
                        .findByUserOrderByScannedAtDesc(currentUser);

        for (UrlScan scan : urlScans) {

            // Type filter
            if (!normalizedType.isBlank()
                    && !normalizedType.equals("URL")) {
                continue;
            }

            // Status filter
            if (!normalizedStatus.isBlank()
                    && !scan.getStatus()
                    .equalsIgnoreCase(normalizedStatus)) {
                continue;
            }

            // Search filter
            if (!normalizedSearch.isBlank()
                    && !scan.getUrl()
                    .toLowerCase(Locale.ROOT)
                    .contains(normalizedSearch)) {
                continue;
            }

            history.add(
                    new ScanHistoryResponse(
                            scan.getId(),
                            "URL",
                            scan.getUrl(),
                            scan.getRiskScore(),
                            scan.getStatus(),
                            scan.getScannedAt()
                    )
            );
        }

        // =========================
        // Email Scans
        // =========================

        List<EmailScan> emailScans =
                emailScanRepository
                        .findAllByUserOrderByScannedAtDesc(currentUser);

        for (EmailScan scan : emailScans) {

            // Type filter
            if (!normalizedType.isBlank()
                    && !normalizedType.equals("EMAIL")) {
                continue;
            }

            // Status filter
            if (!normalizedStatus.isBlank()
                    && !scan.getStatus()
                    .equalsIgnoreCase(normalizedStatus)) {
                continue;
            }

            // Search sender OR subject
            boolean matchesSearch =
                    normalizedSearch.isBlank()
                            || scan.getSender()
                            .toLowerCase(Locale.ROOT)
                            .contains(normalizedSearch)
                            || scan.getSubject()
                            .toLowerCase(Locale.ROOT)
                            .contains(normalizedSearch);

            if (!matchesSearch) {
                continue;
            }

            history.add(
                    new ScanHistoryResponse(
                            scan.getId(),
                            "EMAIL",
                            scan.getSender(),
                            scan.getRiskScore(),
                            scan.getStatus(),
                            scan.getScannedAt()
                    )
            );
        }

        // =========================
        // Sort newest first
        // =========================

        history.sort(
                Comparator.comparing(
                        ScanHistoryResponse::getScannedAt
                ).reversed()
        );

        return history;
    }

    // =========================
    // Reports
    // =========================

    public ScanReportResponse getReport(User currentUser) {

        List<UrlScan> urlScans =
                urlScanRepository
                        .findByUserOrderByScannedAtDesc(currentUser);

        List<EmailScan> emailScans =
                emailScanRepository
                        .findAllByUserOrderByScannedAtDesc(currentUser);

        long totalScans =
                urlScans.size() + emailScans.size();

        long safe = 0;
        long suspicious = 0;
        long malicious = 0;

        int totalRiskScore = 0;

        // =========================
        // Process URL scans
        // =========================

        for (UrlScan scan : urlScans) {

            totalRiskScore += scan.getRiskScore();

            switch (scan.getStatus().toUpperCase(Locale.ROOT)) {

                case "SAFE" -> safe++;

                case "SUSPICIOUS" -> suspicious++;

                case "MALICIOUS" -> malicious++;
            }
        }

        // =========================
        // Process Email scans
        // =========================

        for (EmailScan scan : emailScans) {

            totalRiskScore += scan.getRiskScore();

            switch (scan.getStatus().toUpperCase(Locale.ROOT)) {

                case "SAFE" -> safe++;

                case "SUSPICIOUS" -> suspicious++;

                case "MALICIOUS" -> malicious++;
            }
        }

        // =========================
        // Average Risk Score
        // =========================

        double averageRiskScore =
                totalScans == 0
                        ? 0.0
                        : (double) totalRiskScore / totalScans;

        // Round to 2 decimal places
        averageRiskScore =
                Math.round(averageRiskScore * 100.0) / 100.0;

        return new ScanReportResponse(
                totalScans,
                urlScans.size(),
                emailScans.size(),
                safe,
                suspicious,
                malicious,
                averageRiskScore
        );
    }
}