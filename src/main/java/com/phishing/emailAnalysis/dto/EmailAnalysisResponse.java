package com.phishing.emailAnalysis.dto;

import java.util.List;

public class EmailAnalysisResponse {
    private Long id;
    private final String sender;
    private final String subject;
    private final boolean suspiciousKeyword;
    private final boolean urgencyDetechted;
    private final boolean credentialDetected;
    private final List<String> extractUrls;
    private final int riskScore;
    private final String status;

    public EmailAnalysisResponse(
            Long id,
            String sender,
            String subject,
            boolean suspiciousKeyword,
            boolean urgencyDetechted,
            boolean credentialDetected,
            List<String> extractUrls,
            int riskScore,
            String status){
        this.id = id;
        this.sender = sender;
        this.subject = subject;
        this.suspiciousKeyword =suspiciousKeyword;
        this.urgencyDetechted = urgencyDetechted;
        this.credentialDetected =credentialDetected;
        this.extractUrls =extractUrls;
        this.riskScore =riskScore;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public boolean isSuspiciousKeyword() {
        return suspiciousKeyword;
    }

    public boolean isUrgencyDetechted() {
        return urgencyDetechted;
    }

    public boolean isCredentialDetected() {
        return credentialDetected;
    }

    public List<String> getExtractUrls() {
        return extractUrls;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getStatus() {
        return status;
    }
}
