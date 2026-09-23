package com.phishing.Services;

import com.phishing.DTO.AdminStatisticsResponse;
import com.phishing.DTO.ThreatAnalyticsResponse;
import com.phishing.emailAnalysis.model.EmailScan;
import com.phishing.emailAnalysis.repository.EmailScanRepository;
import com.phishing.scanHistory.dto.ScanHistoryResponse;
import com.phishing.urlAnalysis.model.UrlScan;
import com.phishing.urlAnalysis.repository.UrlScanRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class AdminDashboardService {

    private final UrlScanRepository urlScanRepository;
    private final EmailScanRepository emailScanRepository;

    public AdminDashboardService(
            UrlScanRepository urlScanRepository,
            EmailScanRepository emailScanRepository) {

        this.urlScanRepository = urlScanRepository;
        this.emailScanRepository = emailScanRepository;
    }

    public List<ScanHistoryResponse> getAllScans() {

        List<ScanHistoryResponse> scans = new ArrayList<>();

        urlScanRepository.findAllByOrderByScannedAtDesc().forEach(scan ->
                scans.add(new ScanHistoryResponse(
                        scan.getId(), "URL", scan.getUrl(), scan.getRiskScore(),
                        scan.getStatus(), scan.getScannedAt())));

        emailScanRepository.findAll().forEach(scan ->
                scans.add(new ScanHistoryResponse(
                        scan.getId(), "EMAIL", scan.getSender() + " — " + scan.getSubject(),
                        scan.getRiskScore(), scan.getStatus(), scan.getScannedAt())));

        scans.sort(Comparator.comparing(ScanHistoryResponse::getScannedAt).reversed());
        return scans;
    }
    
    public ThreatAnalyticsResponse getThreatAnalytics() {

        long safeScans =
                urlScanRepository.countByStatus("SAFE");

        long suspiciousScans =
                urlScanRepository.countByStatus("SUSPICIOUS");

        long maliciousScans =
                urlScanRepository.countByStatus("MALICIOUS")
                        + emailScanRepository.countByStatus("MALICIOUS");

        safeScans += emailScanRepository.countByStatus("SAFE");
        suspiciousScans += emailScanRepository.countByStatus("SUSPICIOUS");

        return new ThreatAnalyticsResponse(
                safeScans,
                suspiciousScans,
                maliciousScans
        );
    }
    
    public AdminStatisticsResponse getStatistics() {

        long totalScans = urlScanRepository.count() + emailScanRepository.count();

        long safeScans =
                urlScanRepository.countByStatus("SAFE")
                        + emailScanRepository.countByStatus("SAFE");

        long suspiciousScans =
                urlScanRepository.countByStatus("SUSPICIOUS")
                        + emailScanRepository.countByStatus("SUSPICIOUS");

        long maliciousScans =
                urlScanRepository.countByStatus("MALICIOUS")
                        + emailScanRepository.countByStatus("MALICIOUS");

        List<UrlScan> urlScans = urlScanRepository.findAllByOrderByScannedAtDesc();
        List<EmailScan> emailScans = emailScanRepository.findAll();

        double averageRiskScore = 0.0;

        if (totalScans > 0) {

            int totalRiskScore = urlScans.stream()
                    .mapToInt(UrlScan::getRiskScore)
                    .sum()
                    + emailScans.stream()
                    .mapToInt(EmailScan::getRiskScore)
                    .sum();

            averageRiskScore =
                    (double) totalRiskScore / totalScans;

            averageRiskScore =
                    Math.round(averageRiskScore * 100.0) / 100.0;
        }

        return new AdminStatisticsResponse(
                totalScans,
                safeScans,
                suspiciousScans,
                maliciousScans,
                averageRiskScore
        );
    }
}
