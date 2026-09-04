package com.phishing.urlAnalysis.dto;

import com.phishing.urlAnalysis.model.UrlScan;

import java.time.LocalDateTime;

public class ScanHistoryResponse {

    private final Long id;
    private final String url;
    private final String status;
    private final int riskScore;
    private final boolean https;
    private final boolean suspiciousKeyword;
    private final boolean suspiciousDomain;
    private final boolean ipAddress;
    private final String resolvedIp;
    private final LocalDateTime scannedAt;

    public ScanHistoryResponse(UrlScan scan) {
        this.id = scan.getId();
        this.url = scan.getUrl();
        this.status = scan.getStatus();
        this.riskScore = scan.getRiskScore();
        this.https = scan.isHttps();
        this.suspiciousKeyword = scan.isSuspiciousKeyword();
        this.suspiciousDomain = scan.isSuspiciousDomain();
        this.ipAddress = scan.isIpAddress();
        this.resolvedIp = scan.getResolvedIp();
        this.scannedAt = scan.getScannedAt();
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getStatus() {
        return status;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public boolean isHttps() {
        return https;
    }

    public boolean isSuspiciousKeyword() {
        return suspiciousKeyword;
    }

    public boolean isSuspiciousDomain() {
        return suspiciousDomain;
    }

    public boolean isIpAddress() {
        return ipAddress;
    }

    public String getResolvedIp() {
        return resolvedIp;
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }
}