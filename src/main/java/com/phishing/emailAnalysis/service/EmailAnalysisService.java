package com.phishing.emailAnalysis.service;

import com.phishing.emailAnalysis.dto.EmailAnalysisRequest;
import com.phishing.urlAnalysis.service.KeywordDetectionService;
import org.springframework.stereotype.Service;

@Service
public class EmailAnalysisService {
    private final KeywordDetectionService keywordDetectionService;

    public EmailAnalysisService( KeywordDetectionService keywordDetectionService){
        this.keywordDetectionService = keywordDetectionService;
    }

    public void analyzeEmail(EmailAnalysisRequest request) {
        boolean suspiciousKeyword =
                keywordDetectionService.containsSuspiciousKeyword(
                        request.getSubject(),
                        request.getBody()
                );
        System.out.println("Suspicious Keyword: " + suspiciousKeyword);
    }
}