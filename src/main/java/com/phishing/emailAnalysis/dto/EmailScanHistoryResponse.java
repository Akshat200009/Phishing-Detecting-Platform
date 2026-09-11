package com.phishing.emailAnalysis.dto;

import com.phishing.emailAnalysis.model.EmailScan;

import java.time.LocalDateTime;

public class EmailScanHistoryResponse {
    private final Long id;
    private final String sender;
    private final String subject;
    private final int riskScore;
    private final String status;
    private final boolean suspiciousKeyword;
    private final boolean urgencyDetected;
    private final boolean credentialDetected;
    private final LocalDateTime scannedAt;

    public EmailScanHistoryResponse(EmailScan scan){
        this.id = scan.getId();
        this.sender = scan.getSender();
        this.subject = scan.getSubject();
        this.riskScore = scan.getRiskScore();
        this.status = scan.getStatus();
        this.suspiciousKeyword = scan.isSuspiciousKeywords();
        this.urgencyDetected = scan.isUrgencyDetected();
        this.credentialDetected = scan.isCredentialDetected();
        this.scannedAt = scan.getScannedAt();
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }

    public boolean isCredentialDetected() {
        return credentialDetected;
    }

    public boolean isUrgencyDetected() {
        return urgencyDetected;
    }

    public boolean isSuspiciousKeyword() {
        return suspiciousKeyword;
    }

    public String getStatus() {
        return status;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getSubject() {
        return subject;
    }

    public String getSender() {
        return sender;
    }

    public Long getId() {
        return id;
    }
}
