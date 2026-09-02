package com.phishing.urlAnalysis.DTO;

import java.time.LocalDateTime;

public class UrlScanResponse {
    private Long id;
    private String url;
    private boolean valid;
    private String status;
    private LocalDateTime scannedAt;
    private boolean https;

    public UrlScanResponse(Long id, String url, boolean valid,
                           String status, LocalDateTime scannedAt, boolean https){
        this.id = id;
        this.url = url;
        this.valid = valid;
        this.status = status;
        this.scannedAt = scannedAt;
        this.https = https;
    }

    public String getUrl(){
        return url;
    }

    public boolean isValid(){
        return valid;
    }

    public Long getId() {
        return id;
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
}