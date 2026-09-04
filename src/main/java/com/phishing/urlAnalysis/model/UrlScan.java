package com.phishing.urlAnalysis.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_scans")
public class UrlScan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime scannedAt;

    @Column(nullable = false)
    private boolean https;

    @Column(nullable = false)
    private int riskScore;

    @Column(nullable = false)
    private boolean suspiciousKeyword;

    @Column(nullable = false)
    private boolean suspiciousDomain;

    @Column(nullable = false)
    private boolean ipAddress;

    private String resolvedIp;

    protected UrlScan() {
        // Required by JPA
    }

    public UrlScan(
            String url,
            String status,
            boolean https,
            int riskScore,
            boolean suspiciousKeyword,
            boolean suspiciousDomain,
            boolean ipAddress,
            String resolvedIp) {

        this.url = url;
        this.status = status;
        this.https = https;
        this.riskScore = riskScore;
        this.suspiciousKeyword = suspiciousKeyword;
        this.suspiciousDomain = suspiciousDomain;
        this.ipAddress = ipAddress;
        this.resolvedIp = resolvedIp;
        this.scannedAt = LocalDateTime.now();
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
}