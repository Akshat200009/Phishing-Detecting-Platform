package com.phishing.urlAnalysis.service;

import com.phishing.urlAnalysis.DTO.UrlScanResponse;
import com.phishing.urlAnalysis.model.UrlScan;
import com.phishing.urlAnalysis.repository.UrlScanRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlAnalysisService {
    private final UrlValidationService urlValidationService;
    private final UrlScanRepository urlScanRepository;
    private final HttpsCheckService httpsCheckService;

    public UrlAnalysisService(UrlValidationService urlValidationService,
                              UrlScanRepository urlScanRepository,
                              HttpsCheckService httpsCheckService){
        this.urlValidationService = urlValidationService;
        this.urlScanRepository = urlScanRepository;
        this.httpsCheckService = httpsCheckService;
    }

    public UrlScanResponse analyzeUrl(String url){
        boolean valid = urlValidationService.isValidUrl(url);
        boolean https = valid && httpsCheckService.isHttps(url);
        String status;

        if (!valid){
            status = "INVALID";
        } else if (!https) {
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