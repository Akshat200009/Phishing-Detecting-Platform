package com.phishing.Controllers;

import com.phishing.DTO.UpdateProfileRequest;
import com.phishing.DTO.UpdateProfileResponse;
import com.phishing.DTO.UserProfileResponse;
import com.phishing.Entities.User;
import com.phishing.Services.JwtService;
import com.phishing.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(
            UserService userService,
            JwtService jwtService) {

        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getProfile(
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        UserProfileResponse response =
                new UserProfileResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getRole().name()
                );

        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<UpdateProfileResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateProfileRequest request) {

        User user = (User) authentication.getPrincipal();

        User updatedUser =
                userService.updateProfile(user, request);

        String newToken =
                jwtService.generateToken(updatedUser.getEmail(),  
                		updatedUser.getRole().name());

        UserProfileResponse profileResponse =
                new UserProfileResponse(
                        updatedUser.getId(),
                        updatedUser.getName(),
                        updatedUser.getEmail(),
                        updatedUser.getRole().name()
                );

        UpdateProfileResponse response =
                new UpdateProfileResponse(
                        profileResponse,
                        newToken
                );

        return ResponseEntity.ok(response);
    }
}