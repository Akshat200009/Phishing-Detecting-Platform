package com.phishing.urlAnalysis.dto;

public class VirusTotalResult {
    private String status;
    private int malicious;
    private int suspicious;
    private int harmless;
    private int undetected;

    public VirusTotalResult(
            String status,
            int malicious,
            int suspicious,
            int harmless,
            int undetected
    ){
        this.status = status;
        this.malicious = malicious;
        this.suspicious = suspicious;
        this.harmless = harmless;
        this.undetected = undetected;
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

    public int getUndetected() {
        return undetected;
    }

    public boolean isSuspicious() {
        return suspicious > 0;
    }

    public boolean isCompleted(){
        return "completed".equalsIgnoreCase(status);
    }
}
