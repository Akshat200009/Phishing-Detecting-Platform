package com.phishing.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phishing.DTO.LoginRequest;
import com.phishing.DTO.LoginResponse;
import com.phishing.DTO.RegisterRequest;
import com.phishing.DTO.RegisterResponse;
import com.phishing.Entities.User;
import com.phishing.Services.AuthService;
import com.phishing.Services.TokenBlacklistService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	private final TokenBlacklistService tokenBlacklistService;

	public AuthController(AuthService authService, TokenBlacklistService tokenBlacklistService) {
		this.authService = authService;
		this.tokenBlacklistService=tokenBlacklistService;
	}

	// ----- Register User API-------------------------------------

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {

		User registeredUser = authService.registerUser(request);

		RegisterResponse response = new RegisterResponse(
				registeredUser.getId(), 
				registeredUser.getName(),
				registeredUser.getEmail(),
				registeredUser.getRole(), 
				registeredUser.getCreatedAt());

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// ---------Login User API------------------------------------------
	
	
    @PostMapping("/login")
	public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request) {
		String token = authService.loginUser(request);
		LoginResponse response = new LoginResponse(token, "bearer");
		return ResponseEntity.ok(response);
	}
    
    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            HttpServletRequest request) {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null ||
                !authorizationHeader.startsWith("Bearer ")) {

            return ResponseEntity.badRequest()
                    .body("Authorization token is required");
        }

        String token =
                authorizationHeader.substring(7);

        tokenBlacklistService.blacklistToken(token);

        return ResponseEntity.ok("Logout successful");
    }

}
