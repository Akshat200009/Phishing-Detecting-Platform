package com.phishing.urlAnalysis.dto;

public class SafeBrowsingResult {
    private final boolean threatDetected;
    private final String threatType;

    public SafeBrowsingResult(boolean threatDetected,
                              String threatType){
        this.threatDetected = threatDetected;
        this.threatType = threatType;
    }

    public boolean isThreatDetected(){
        return threatDetected;
    }

    public String getThreatType(){
        return threatType;
    }
}
