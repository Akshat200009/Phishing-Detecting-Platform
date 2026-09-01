package com.phishing.urlAnalysis.service;

import com.phishing.urlAnalysis.DTO.UrlScanResponse;
import com.phishing.urlAnalysis.model.UrlScan;
import com.phishing.urlAnalysis.repository.UrlScanRepository;
import org.springframework.stereotype.Service;

@Service
public class UrlAnalysisService {
    private final UrlValidationService urlValidationService;
    private final UrlScanRepository urlScanRepository;

    public UrlAnalysisService(UrlValidationService urlValidationService, UrlScanRepository urlScanRepository){
        this.urlValidationService = urlValidationService;
        this.urlScanRepository = urlScanRepository;
    }

    public UrlScanResponse analyzeUrl(String url){
        boolean valid = urlValidationService.isValidUrl(url);
        String status = valid ? "VALID" : "INVALID";
        UrlScan urlScan = new UrlScan(url, status);
        UrlScan savedScan = urlScanRepository.save(urlScan);
        return new UrlScanResponse(
                savedScan.getId(),
                savedScan.getUrl(),
                valid,
                savedScan.getStatus(),
                savedScan.getScannedAt()
        );
    }
}