package com.phishing.Services;

import com.phishing.DTO.AdminStatisticsResponse;
import com.phishing.DTO.ThreatAnalyticsResponse;
import com.phishing.urlAnalysis.dto.ScanHistoryResponse;
import com.phishing.urlAnalysis.model.UrlScan;
import com.phishing.urlAnalysis.repository.UrlScanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminDashboardService {

    private final UrlScanRepository urlScanRepository;

    public AdminDashboardService(
            UrlScanRepository urlScanRepository) {

        this.urlScanRepository = urlScanRepository;
    }

    public List<ScanHistoryResponse> getAllScans() {

        return urlScanRepository
                .findAllByOrderByScannedAtDesc()
                .stream()
                .map(ScanHistoryResponse::new)
                .toList();
    }
    
    public ThreatAnalyticsResponse getThreatAnalytics() {

        long safeScans =
                urlScanRepository.countByStatus("SAFE");

        long suspiciousScans =
                urlScanRepository.countByStatus("SUSPICIOUS");

        long maliciousScans =
                urlScanRepository.countByStatus("MALICIOUS");

        return new ThreatAnalyticsResponse(
                safeScans,
                suspiciousScans,
                maliciousScans
        );
    }
    
    public AdminStatisticsResponse getStatistics() {

        long totalScans =
                urlScanRepository.count();

        long safeScans =
                urlScanRepository.countByStatus("SAFE");

        long suspiciousScans =
                urlScanRepository.countByStatus("SUSPICIOUS");

        long maliciousScans =
                urlScanRepository.countByStatus("MALICIOUS");

        List<UrlScan> scans =
                urlScanRepository.findAllByOrderByScannedAtDesc();

        double averageRiskScore = 0.0;

        if (!scans.isEmpty()) {

            int totalRiskScore = scans.stream()
                    .mapToInt(UrlScan::getRiskScore)
                    .sum();

            averageRiskScore =
                    (double) totalRiskScore / scans.size();

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