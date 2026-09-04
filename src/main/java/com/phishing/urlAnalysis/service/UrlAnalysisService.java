package com.phishing.urlAnalysis.service;

import com.phishing.Exception.ScanNotFoundException;
import com.phishing.urlAnalysis.dto.SafeBrowsingResult;
import com.phishing.urlAnalysis.dto.UrlScanResponse;
import com.phishing.urlAnalysis.dto.VirusTotalResult;
import com.phishing.urlAnalysis.model.UrlScan;
import com.phishing.urlAnalysis.repository.UrlScanRepository;
import org.springframework.stereotype.Service;
import com.phishing.urlAnalysis.dto.ScanHistoryResponse;
import java.util.List;

@Service
public class UrlAnalysisService {
    private final UrlValidationService urlValidationService;
    private final UrlScanRepository urlScanRepository;
    private final HttpsCheckService httpsCheckService;
    private final KeywordDetectionService keywordDetectionService;
    private final DomainAnalysisService domainAnalysisService;
    private final IpDetectionService ipDetectionService;
    private final VirusTotalService virusTotalService;
    private final SafeBrowsingService safeBrowsingService;
    private final RiskScoreService riskScoreService;

    public UrlAnalysisService(UrlValidationService urlValidationService,
                              UrlScanRepository urlScanRepository,
                              HttpsCheckService httpsCheckService,
                              KeywordDetectionService keywordDetectionService,
                              DomainAnalysisService domainAnalysisService,
                              IpDetectionService ipDetectionService,
                              VirusTotalService virusTotalService,
                              SafeBrowsingService safeBrowsingService,
                              RiskScoreService riskScoreService){
        this.urlValidationService = urlValidationService;
        this.urlScanRepository = urlScanRepository;
        this.httpsCheckService = httpsCheckService;
        this.keywordDetectionService = keywordDetectionService;
        this.domainAnalysisService = domainAnalysisService;
        this.ipDetectionService = ipDetectionService;
        this.virusTotalService = virusTotalService;
        this.safeBrowsingService = safeBrowsingService;
        this.riskScoreService = riskScoreService;
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

        VirusTotalResult virusTotalResult =
                valid
                ? virusTotalService.scanUrl(url)
                        : VirusTotalResult.unavailable();

        SafeBrowsingResult safeBrowsingResult =
                valid
                ? safeBrowsingService.checkUrl(url)
                        : new SafeBrowsingResult(false, "INVALID_URL");

        int riskScore = valid
                ? riskScoreService.calculateRiskScore(
                        https,
                suspiciousKeyword,
                suspiciousDomain,
                ipAddress,
                virusTotalResult,
                safeBrowsingResult
        ) : 0;

        status = valid
                ? riskScoreService.determineStatus(riskScore) : "INVALID";

        UrlScan urlScan = new UrlScan(
                url,
                status,
                https,
                riskScore,
                suspiciousKeyword,
                suspiciousDomain,
                ipAddress,
                resolveIp
        );
        UrlScan savedScan = urlScanRepository.save(urlScan);

        return new UrlScanResponse(
                savedScan.getId(),
                savedScan.getUrl(),
                valid,
                savedScan.getStatus(),
                savedScan.getScannedAt(),
                https,
                riskScore,
                suspiciousKeyword,
                suspiciousDomain,
                ipAddress,
                resolveIp,
                virusTotalResult,
                safeBrowsingResult
        );
    }
    public List<ScanHistoryResponse> getScanHistory() {

        return urlScanRepository
                .findAllByOrderByScannedAtDesc()
                .stream()
                .map(ScanHistoryResponse::new)
                .toList();
    }

    public ScanHistoryResponse getScanById(Long id) {

        UrlScan scan = urlScanRepository.findById(id)
                .orElseThrow(() ->
                        new ScanNotFoundException("Scan not found with id: " + id)
                );

        return new ScanHistoryResponse(scan);
    }
}