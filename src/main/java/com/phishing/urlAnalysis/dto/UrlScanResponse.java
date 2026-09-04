package com.phishing.urlAnalysis.dto;

import java.time.LocalDateTime;

public class UrlScanResponse {
    private Long id;
    private String url;
    private boolean valid;
    private String status;
    private LocalDateTime scannedAt;
    private boolean https;
    private final int riskScore;
    private final boolean suspiciousKeyword;
    private final boolean suspiciousDomain;
    private final boolean ipAddress;
    private final String resolvedIp;
    private final VirusTotalResult virusTotalResult;
    private final SafeBrowsingResult safeBrowsingResult;

    public UrlScanResponse(Long id, String url, boolean valid,
                           String status, LocalDateTime scannedAt, boolean https,
                           int riskScore, boolean suspiciousKeyword,
                           boolean suspiciousDomain, boolean ipAddress,
                           String resolvedIp, VirusTotalResult virusTotalResult,
                           SafeBrowsingResult safeBrowsingResult){
        this.id = id;
        this.url = url;
        this.valid = valid;
        this.status = status;
        this.scannedAt = scannedAt;
        this.https = https;
        this.riskScore = riskScore;
        this.suspiciousKeyword = suspiciousKeyword;
        this.suspiciousDomain = suspiciousDomain;
        this.ipAddress = ipAddress;
        this.resolvedIp = resolvedIp;
        this.virusTotalResult =  virusTotalResult;
        this.safeBrowsingResult = safeBrowsingResult;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public boolean isValid() {
        return valid;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }

    public boolean isHttps() {
        return https;
    }

    public int getRiskScore() {
        return riskScore;
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

    public VirusTotalResult getVirusTotal() {
        return virusTotalResult;
    }

    public SafeBrowsingResult getSafeBrowsing() {
        return safeBrowsingResult;
    }
}