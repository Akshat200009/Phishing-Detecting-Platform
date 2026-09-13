package com.phishing.scanHistory.controller;

import com.phishing.Entities.User;
import com.phishing.scanHistory.dto.ScanHistoryResponse;
import com.phishing.scanHistory.dto.ScanReportResponse;
import com.phishing.scanHistory.service.ScanHistoryService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class ScanHistoryController {

    private final ScanHistoryService scanHistoryService;

    public ScanHistoryController(
            ScanHistoryService scanHistoryService) {

        this.scanHistoryService = scanHistoryService;
    }

    @GetMapping
    public List<ScanHistoryResponse> getHistory(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String status,
            Authentication authentication) {

        User currentUser =
                (User) authentication.getPrincipal();

        return scanHistoryService.getAllHistory(
                currentUser,
                search,
                type,
                status
        );
    }

    @GetMapping("/reports")
    public ScanReportResponse getReport(
            Authentication authentication) {

        User currentUser =
                (User) authentication.getPrincipal();

        return scanHistoryService.getReport(currentUser);
    }
}