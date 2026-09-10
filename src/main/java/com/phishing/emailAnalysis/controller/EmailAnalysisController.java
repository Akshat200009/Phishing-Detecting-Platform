package com.phishing.emailAnalysis.controller;

import com.phishing.Entities.User;
import com.phishing.emailAnalysis.dto.EmailAnalysisRequest;
import com.phishing.emailAnalysis.dto.EmailAnalysisResponse;
import com.phishing.emailAnalysis.service.EmailAnalysisService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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
}