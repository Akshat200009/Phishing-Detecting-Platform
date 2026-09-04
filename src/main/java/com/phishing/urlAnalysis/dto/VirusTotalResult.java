package com.phishing.urlAnalysis.dto;

public class VirusTotalResult {

    private final String status;
    private final int malicious;
    private final int suspicious;
    private final int harmless;

    public VirusTotalResult(
            String status,
            int malicious,
            int suspicious,
            int harmless) {

        this.status = status;
        this.malicious = malicious;
        this.suspicious = suspicious;
        this.harmless = harmless;
    }

    public String getStatus() {
        return status;
    }

    public int getMalicious() {
        return malicious;
    }

    public int getSuspicious() {
        return suspicious;
    }

    public int getHarmless() {
        return harmless;
    }

    public boolean isMalicious() {
        return malicious > 0;
    }

    public boolean isSuspicious() {
        return suspicious > 0;
    }

    public boolean isCompleted() {
        return "completed".equalsIgnoreCase(status);
    }

    public static VirusTotalResult unavailable() {
        return new VirusTotalResult(
                "unavailable",
                0,
                0,
                0
        );
    }
}