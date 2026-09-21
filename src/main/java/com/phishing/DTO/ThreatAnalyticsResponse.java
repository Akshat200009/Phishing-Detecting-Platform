package com.phishing.DTO;

public class ThreatAnalyticsResponse {

    private long safeScans;
    private long suspiciousScans;
    private long maliciousScans;

    public ThreatAnalyticsResponse() {
    }

    public ThreatAnalyticsResponse(
            long safeScans,
            long suspiciousScans,
            long maliciousScans) {

        this.safeScans = safeScans;
        this.suspiciousScans = suspiciousScans;
        this.maliciousScans = maliciousScans;
    }

    public long getSafeScans() {
        return safeScans;
    }

    public void setSafeScans(long safeScans) {
        this.safeScans = safeScans;
    }

    public long getSuspiciousScans() {
        return suspiciousScans;
    }

    public void setSuspiciousScans(long suspiciousScans) {
        this.suspiciousScans = suspiciousScans;
    }

    public long getMaliciousScans() {
        return maliciousScans;
    }

    public void setMaliciousScans(long maliciousScans) {
        this.maliciousScans = maliciousScans;
    }
}