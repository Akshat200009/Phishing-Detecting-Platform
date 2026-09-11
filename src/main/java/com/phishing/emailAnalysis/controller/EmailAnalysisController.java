package com.phishing.emailAnalysis.controller;

import com.phishing.Entities.User;
import com.phishing.emailAnalysis.dto.EmailAnalysisRequest;
import com.phishing.emailAnalysis.dto.EmailAnalysisResponse;
import com.phishing.emailAnalysis.dto.EmailScanHistoryResponse;
import com.phishing.emailAnalysis.service.EmailAnalysisService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/email")
public class EmailAnalysisController {

    private final EmailAnalysisService emailAnalysisService;

    public EmailAnalysisController(
            EmailAnalysisService emailAnalysisService) {
        this.emailAnalysisService = emailAnalysisService;
    }

    @PostMapping("/scan")
    public EmailAnalysisResponse scanEmail(
            @Valid @RequestBody EmailAnalysisRequest request,
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return emailAnalysisService.analyzeEmail(request, user);
    }

    @GetMapping("/scans")
    public List<EmailScanHistoryResponse> getScanHistory(
            Authentication authentication) {

        User currentUser =
                (User) authentication.getPrincipal();

        return emailAnalysisService
                .getScanHistory(currentUser);
    }

    @GetMapping("/scans/{id}")
    public EmailScanHistoryResponse getScanById(
            @PathVariable Long id,
            Authentication authentication) {

        User currentUser =
                (User) authentication.getPrincipal();

        return emailAnalysisService
                .getScanById(id, currentUser);
    }
}