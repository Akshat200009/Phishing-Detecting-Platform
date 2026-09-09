package com.phishing.emailAnalysis.service;

import com.phishing.emailAnalysis.dto.EmailAnalysisRequest;
import com.phishing.urlAnalysis.service.KeywordDetectionService;
import org.springframework.stereotype.Service;

@Service
public class EmailAnalysisService {
    private final KeywordDetectionService keywordDetectionService;
    private final UrgencyDetectionService urgencyDetectionService;
    private final CredentialDetectionService credentialDetectionService;

    public EmailAnalysisService( KeywordDetectionService keywordDetectionService,
                                 UrgencyDetectionService urgencyDetectionService,
                                 CredentialDetectionService credentialDetectionService){
        this.keywordDetectionService = keywordDetectionService;
        this.urgencyDetectionService = urgencyDetectionService;
        this.credentialDetectionService = credentialDetectionService;
    }

    public void analyzeEmail(EmailAnalysisRequest request) {
        boolean suspiciousKeyword =
                keywordDetectionService.containsSuspiciousKeyword(
                        request.getSubject(),
                        request.getBody()
                );
        boolean urgencyDetected  = urgencyDetectionService.containsUrgency(
                request.getSubject(),
                request.getBody()
        );
        boolean credentialDetected = credentialDetectionService.containsCredentialRequest(
                request.getSubject(),
                request.getBody()
        );

        System.out.println("Suspicious Keyword: " + suspiciousKeyword);
        System.out.println("Urgency detected: " + urgencyDetected);
        System.out.println("Credential Request Detected : " + credentialDetected);
    }
}