package com.phishing.emailAnalysis.controller;

import com.phishing.emailAnalysis.dto.EmailAnalysisRequest;
import com.phishing.emailAnalysis.service.EmailAnalysisService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailAnalysisController {
    private final EmailAnalysisService emailAnalysisService;

    public EmailAnalysisController(EmailAnalysisService emailAnalysisService){
        this.emailAnalysisService = emailAnalysisService;
    }

    @PostMapping("/scan")
    public String scanEmail(@Valid @RequestBody EmailAnalysisRequest request){
        return "Email analysis started";
    }
}