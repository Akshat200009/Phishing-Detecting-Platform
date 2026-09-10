package com.phishing.Controllers;

import com.phishing.DTO.UserDashboardResponse;
import com.phishing.Entities.User;
import com.phishing.Services.UserDashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserDashboardController {

    private final UserDashboardService userDashboardService;

    public UserDashboardController(
            UserDashboardService userDashboardService) {

        this.userDashboardService = userDashboardService;
    }

    @GetMapping("/dashboard")
    public ResponseEntity<UserDashboardResponse> getDashboard(
            Authentication authentication) {

        User currentUser =
                (User) authentication.getPrincipal();

        UserDashboardResponse dashboard =
                userDashboardService.getDashboard(currentUser);

        return ResponseEntity.ok(dashboard);
    }
}