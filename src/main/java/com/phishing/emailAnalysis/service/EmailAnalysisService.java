package com.phishing.emailAnalysis.service;

import com.phishing.Entities.User;
import com.phishing.emailAnalysis.dto.EmailAnalysisRequest;
import com.phishing.emailAnalysis.dto.EmailAnalysisResponse;
import com.phishing.urlAnalysis.dto.UrlScanResponse;
import com.phishing.urlAnalysis.service.KeywordDetectionService;
import com.phishing.urlAnalysis.service.UrlAnalysisService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailAnalysisService {
    private final KeywordDetectionService keywordDetectionService;
    private final UrgencyDetectionService urgencyDetectionService;
    private final CredentialDetectionService credentialDetectionService;
    private final UrlExtractionService urlExtractionService;
    private final UrlAnalysisService urlAnalysisService;
    private final EmailRiskScoreService emailRiskScoreService;

    public EmailAnalysisService( KeywordDetectionService keywordDetectionService,
                                 UrgencyDetectionService urgencyDetectionService,
                                 CredentialDetectionService credentialDetectionService,
                                 UrlExtractionService urlExtractionService,
                                 UrlAnalysisService urlAnalysisService,
                                 EmailRiskScoreService emailRiskScoreService){
        this.keywordDetectionService = keywordDetectionService;
        this.urgencyDetectionService = urgencyDetectionService;
        this.credentialDetectionService = credentialDetectionService;
        this.urlExtractionService = urlExtractionService;
        this.urlAnalysisService = urlAnalysisService;
        this.emailRiskScoreService =emailRiskScoreService;
    }

    public EmailAnalysisResponse analyzeEmail(
            EmailAnalysisRequest request,
            User user) {

        boolean suspiciousKeyword =
                keywordDetectionService.containsSuspiciousKeyword(
                        request.getSubject(),
                        request.getBody()
                );

        boolean urgencyDetected =
                urgencyDetectionService.containsUrgency(
                        request.getSubject(),
                        request.getBody()
                );

        boolean credentialDetected =
                credentialDetectionService.containsCredentialRequest(
                        request.getSubject(),
                        request.getBody()
                );

        List<String> extractedUrls =
                urlExtractionService.extractUrls(
                        request.getBody()
                );

        int highestUrlRiskScore = 0;

        for (String url : extractedUrls) {

            UrlScanResponse urlScanResponse =
                    urlAnalysisService.analyzeUrl(url, user);

            highestUrlRiskScore = Math.max(
                    highestUrlRiskScore,
                    urlScanResponse.getRiskScore()
            );
        }

        int riskScore =
                emailRiskScoreService.calculateRiskScore(
                        suspiciousKeyword,
                        urgencyDetected,
                        credentialDetected,
                        highestUrlRiskScore
                );

        String status =
                emailRiskScoreService.determineStatus(riskScore);

        return new EmailAnalysisResponse(
                request.getSender(),
                request.getSubject(),
                suspiciousKeyword,
                urgencyDetected,
                credentialDetected,
                extractedUrls,
                riskScore,
                status
        );
    }
}