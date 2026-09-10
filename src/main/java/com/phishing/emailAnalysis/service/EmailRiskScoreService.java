package com.phishing.emailAnalysis.service;

import org.springframework.stereotype.Service;

@Service
public class EmailRiskScoreService {
    public int calculateRiskScore(
            boolean suspiciousKeyword,
            boolean urgencyDetected,
            boolean credentialDetected,
            int highestUrlRiskScore){
        int score = 0;

        if (suspiciousKeyword){
            score += 20;
        }
        if (urgencyDetected){
            score += 20;
        }
        if (credentialDetected){
            score += 25;
        }
        score += Math.min(highestUrlRiskScore, 35);

        return Math.min(score, 100);
    }
    public String determineStatus(int riskScore){
        if (riskScore >= 70){
            return "MALICIOUS";
        }
        if (riskScore >= 30){
            return "SUSPICIOUS";
        }
        return "SAFE";
    }
}