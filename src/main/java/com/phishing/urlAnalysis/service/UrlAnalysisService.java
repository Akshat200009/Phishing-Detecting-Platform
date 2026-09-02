package com.phishing.urlAnalysis.service;

import com.phishing.urlAnalysis.dto.UrlScanResponse;
import com.phishing.urlAnalysis.model.UrlScan;
import com.phishing.urlAnalysis.repository.UrlScanRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlAnalysisService {
    private final UrlValidationService urlValidationService;
    private final UrlScanRepository urlScanRepository;
    private final HttpsCheckService httpsCheckService;
    private final KeywordDetectionService keywordDetectionService;
    private final DomainAnalysisService domainAnalysisService;
    private final IpDetectionService ipDetectionService;

    public UrlAnalysisService(UrlValidationService urlValidationService,
                              UrlScanRepository urlScanRepository,
                              HttpsCheckService httpsCheckService,
                              KeywordDetectionService keywordDetectionService,
                              DomainAnalysisService domainAnalysisService,
                              IpDetectionService ipDetectionService){
        this.urlValidationService = urlValidationService;
        this.urlScanRepository = urlScanRepository;
        this.httpsCheckService = httpsCheckService;
        this.keywordDetectionService = keywordDetectionService;
        this.domainAnalysisService = domainAnalysisService;
        this.ipDetectionService = ipDetectionService;
    }

    public UrlScanResponse analyzeUrl(String url){
        boolean valid = urlValidationService.isValidUrl(url);
        boolean https = valid && httpsCheckService.isHttps(url);
        boolean suspiciousKeyword = valid && keywordDetectionService.containsSuspiciousKeyword(url);
        String status;

        String domain = valid ? domainAnalysisService.extractDomain(url) : null;
        boolean suspiciousDomain = valid && domainAnalysisService.hasSuspiciousDomain(url);
        boolean ipAddress = valid && ipDetectionService.isIpAddress(url);

        String resolveIp = valid ? ipDetectionService.resolveIpAddress(url) : null;

        if (!valid){
            status = "INVALID";
        } else if (suspiciousKeyword || suspiciousDomain || !https) {
            status = "SUSPICIOUS";
        } else {
            status = "SAFE";
        }

        UrlScan urlScan = new UrlScan(url, status, https);
        UrlScan savedScan = urlScanRepository.save(urlScan);

        return new UrlScanResponse(
                savedScan.getId(),
                savedScan.getUrl(),
                valid,
                savedScan.getStatus(),
                savedScan.getScannedAt(),
                https
        );
    }
}