package com.phishing.DTO;

import com.phishing.urlAnalysis.dto.ScanHistoryResponse;

import java.util.List;

public class UserDashboardResponse {

    private long totalScans;
    private long safeScans;
    private long suspiciousScans;
    private long maliciousScans;
    private double averageRiskScore;
    private List<ScanHistoryResponse> recentScans;

    public UserDashboardResponse() {
    }

    public UserDashboardResponse(
            long totalScans,
            long safeScans,
            long suspiciousScans,
            long maliciousScans,
            double averageRiskScore,
            List<ScanHistoryResponse> recentScans) {

        this.totalScans = totalScans;
        this.safeScans = safeScans;
        this.suspiciousScans = suspiciousScans;
        this.maliciousScans = maliciousScans;
        this.averageRiskScore = averageRiskScore;
        this.recentScans = recentScans;
    }

    public long getTotalScans() {
        return totalScans;
    }

    public void setTotalScans(long totalScans) {
        this.totalScans = totalScans;
    }

    public long getSafeScans() {
        return safeScans;
    }

    public void setSafeScans(long safeScans) {
        this.safeScans = safeScans;
    }

    public long getSuspiciousScans() {
        return suspiciousScans;
    }

    public void setSuspiciousScans(long suspiciousScans) {
        this.suspiciousScans = suspiciousScans;
    }

    public long getMaliciousScans() {
        return maliciousScans;
    }

    public void setMaliciousScans(long maliciousScans) {
        this.maliciousScans = maliciousScans;
    }

    public double getAverageRiskScore() {
        return averageRiskScore;
    }

    public void setAverageRiskScore(double averageRiskScore) {
        this.averageRiskScore = averageRiskScore;
    }

    public List<ScanHistoryResponse> getRecentScans() {
        return recentScans;
    }

    public void setRecentScans(List<ScanHistoryResponse> recentScans) {
        this.recentScans = recentScans;
    }
}