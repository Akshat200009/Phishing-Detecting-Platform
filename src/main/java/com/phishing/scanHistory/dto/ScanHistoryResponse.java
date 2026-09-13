package com.phishing.scanHistory.dto;

import java.time.LocalDateTime;

public class ScanHistoryResponse {
    private final Long id;
    private final String type;
    private final String target;
    private final int riskScore;
    private final String status;
    private final LocalDateTime scannedAt;

    public ScanHistoryResponse(
            Long id,
            String type,
            String target,
            int riskScore,
            String status,
            LocalDateTime scannedAt){
        this.id = id;
        this.type = type;
        this.target = target;
        this.riskScore = riskScore;
        this.status = status;
        this.scannedAt =scannedAt;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTarget() {
        return target;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }
}
