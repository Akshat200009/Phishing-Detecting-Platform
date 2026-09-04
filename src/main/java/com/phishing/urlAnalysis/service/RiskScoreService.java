package com.phishing.urlAnalysis.service;

import com.phishing.urlAnalysis.dto.SafeBrowsingResult;
import com.phishing.urlAnalysis.dto.VirusTotalResult;
import org.springframework.stereotype.Service;

@Service
public class RiskScoreService {

    public int calculateRiskScore(
            boolean https,
            boolean suspiciousKeyword,
            boolean suspiciousDomain,
            boolean ipAddress,
            VirusTotalResult virusTotalResult,
            SafeBrowsingResult safeBrowsingResult) {

        int score = 0;

        // HTTPS
        if (!https) {
            score += 15;
        }

        // Suspicious keyword
        if (suspiciousKeyword) {
            score += 20;
        }

        // Suspicious domain
        if (suspiciousDomain) {
            score += 25;
        }

        // IP address used instead of a domain
        if (ipAddress) {
            score += 25;
        }

        // VirusTotal
        // Only use VirusTotal results when the analysis is completed
        if (virusTotalResult != null && virusTotalResult.isCompleted()) {

            if (virusTotalResult.isMalicious()) {
                score += 40;
            } else if (virusTotalResult.isSuspicious()) {
                score += 20;
            }
        }

        // Google Safe Browsing
        if (safeBrowsingResult != null
                && safeBrowsingResult.isThreatDetected()) {
            score += 50;
        }

        // Risk score cannot exceed 100
        return Math.min(score, 100);
    }

    public String determineStatus(int riskScore) {

        if (riskScore >= 70) {
            return "MALICIOUS";
        }

        if (riskScore >= 30) {
            return "SUSPICIOUS";
        }

        return "SAFE";
    }
}