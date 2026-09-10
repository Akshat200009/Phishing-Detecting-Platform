package com.phishing.Services;

import com.phishing.DTO.UserDashboardResponse;
import com.phishing.Entities.User;
import com.phishing.urlAnalysis.dto.ScanHistoryResponse;
import com.phishing.urlAnalysis.model.UrlScan;
import com.phishing.urlAnalysis.repository.UrlScanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDashboardService {

    private final UrlScanRepository urlScanRepository;

    public UserDashboardService(
            UrlScanRepository urlScanRepository) {

        this.urlScanRepository = urlScanRepository;
    }

    public UserDashboardResponse getDashboard(User user) {

        // Total scans performed by the logged-in user
        long totalScans =
                urlScanRepository.countByUser(user);

        // Status-wise scan counts
        long safeScans =
                urlScanRepository.countByUserAndStatus(
                        user,
                        "SAFE"
                );

        long suspiciousScans =
                urlScanRepository.countByUserAndStatus(
                        user,
                        "SUSPICIOUS"
                );

        long maliciousScans =
                urlScanRepository.countByUserAndStatus(
                        user,
                        "MALICIOUS"
                );

        // Get user's scans for calculating average risk score
        List<UrlScan> scans =
                urlScanRepository.findByUserOrderByScannedAtDesc(
                        user
                );

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

        // Latest 5 scans for Dashboard
        List<ScanHistoryResponse> recentScans =
                scans.stream()
                        .limit(5)
                        .map(ScanHistoryResponse::new)
                        .toList();

        return new UserDashboardResponse(
                totalScans,
                safeScans,
                suspiciousScans,
                maliciousScans,
                averageRiskScore,
                recentScans
        );
    }
}