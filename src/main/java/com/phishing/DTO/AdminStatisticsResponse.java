package com.phishing.DTO;

public class AdminStatisticsResponse {

    private long totalScans;
    private long safeScans;
    private long suspiciousScans;
    private long maliciousScans;
    private double averageRiskScore;

    public AdminStatisticsResponse() {
    }

    public AdminStatisticsResponse(
            long totalScans,
            long safeScans,
            long suspiciousScans,
            long maliciousScans,
            double averageRiskScore) {

        this.totalScans = totalScans;
        this.safeScans = safeScans;
        this.suspiciousScans = suspiciousScans;
        this.maliciousScans = maliciousScans;
        this.averageRiskScore = averageRiskScore;
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
}