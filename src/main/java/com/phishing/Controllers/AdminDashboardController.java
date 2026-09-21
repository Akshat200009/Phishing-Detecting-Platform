package com.phishing.Controllers;

import com.phishing.DTO.AdminStatisticsResponse;
import com.phishing.DTO.ThreatAnalyticsResponse;
import com.phishing.Services.AdminDashboardService;
import com.phishing.urlAnalysis.dto.ScanHistoryResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/dashboard")
public class AdminDashboardController {

    private final AdminDashboardService adminDashboardService;

    public AdminDashboardController(
            AdminDashboardService adminDashboardService) {

        this.adminDashboardService = adminDashboardService;
    }

    @GetMapping("/scans")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ScanHistoryResponse>> getAllScans() {

        List<ScanHistoryResponse> scans =
                adminDashboardService.getAllScans();

        return ResponseEntity.ok(scans);
    }
    @GetMapping("/threat-analytics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ThreatAnalyticsResponse> getThreatAnalytics() {

        ThreatAnalyticsResponse response =
                adminDashboardService.getThreatAnalytics();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminStatisticsResponse> getStatistics() {

        AdminStatisticsResponse response =
                adminDashboardService.getStatistics();

        return ResponseEntity.ok(response);
    }
}