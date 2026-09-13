package com.phishing.scanHistory.dto;

public class ScanReportResponse {

    private final long totalScans;
    private final long urlScans;
    private final long emailScans;

    private final long safe;
    private final long suspicious;
    private final long malicious;

    private final double averageRiskScore;

    public ScanReportResponse(
            long totalScans,
            long urlScans,
            long emailScans,
            long safe,
            long suspicious,
            long malicious,
            double averageRiskScore) {

        this.totalScans = totalScans;
        this.urlScans = urlScans;
        this.emailScans = emailScans;
        this.safe = safe;
        this.suspicious = suspicious;
        this.malicious = malicious;
        this.averageRiskScore = averageRiskScore;
    }

    public long getTotalScans() {
        return totalScans;
    }

    public long getUrlScans() {
        return urlScans;
    }

    public long getEmailScans() {
        return emailScans;
    }

    public long getSafe() {
        return safe;
    }

    public long getSuspicious() {
        return suspicious;
    }

    public long getMalicious() {
        return malicious;
    }

    public double getAverageRiskScore() {
        return averageRiskScore;
    }
}