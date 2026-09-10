package com.phishing.urlAnalysis.controller;

import com.phishing.Entities.User;
import com.phishing.urlAnalysis.dto.ScanHistoryResponse;
import com.phishing.urlAnalysis.dto.UrlScanRequest;
import com.phishing.urlAnalysis.dto.UrlScanResponse;
import com.phishing.urlAnalysis.service.UrlAnalysisService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/url")
public class UrlAnalysisController {

    private final UrlAnalysisService urlAnalysisService;

    public UrlAnalysisController(
            UrlAnalysisService urlAnalysisService) {

        this.urlAnalysisService = urlAnalysisService;
    }

    @PostMapping("/scan")
    public ResponseEntity<UrlScanResponse> scanUrl(
            @Valid @RequestBody UrlScanRequest request,
            Authentication authentication) {

        User currentUser =
                (User) authentication.getPrincipal();

        UrlScanResponse response =
                urlAnalysisService.analyzeUrl(
                        request.getUrl(),
                        currentUser
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/scans")
    public List<ScanHistoryResponse> getScanHistory(
            Authentication authentication) {

        User currentUser =
                (User) authentication.getPrincipal();

        return urlAnalysisService
                .getScanHistory(currentUser);
    }

    @GetMapping("/scans/{id}")
    public ScanHistoryResponse getScanById(
            @PathVariable Long id,
            Authentication authentication) {

        User currentUser =
                (User) authentication.getPrincipal();

        return urlAnalysisService
                .getScanById(id, currentUser);
    }
}