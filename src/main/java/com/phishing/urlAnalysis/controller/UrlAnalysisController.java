package com.phishing.urlAnalysis.controller;

import com.phishing.urlAnalysis.dto.UrlScanRequest;
import com.phishing.urlAnalysis.dto.UrlScanResponse;
import com.phishing.urlAnalysis.service.UrlAnalysisService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/url")
public class UrlAnalysisController {
    private final UrlAnalysisService urlAnalysisService;

    public UrlAnalysisController(UrlAnalysisService urlAnalysisService){
        this.urlAnalysisService = urlAnalysisService;
    }

    @PostMapping("/scan")
    public ResponseEntity<UrlScanResponse> scanUrl(@Valid @RequestBody UrlScanRequest request){
        UrlScanResponse response = urlAnalysisService.analyzeUrl(request.getUrl());
        return ResponseEntity.ok(response);
    }
}