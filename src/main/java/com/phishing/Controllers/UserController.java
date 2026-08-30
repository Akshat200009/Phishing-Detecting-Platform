package com.phishing.Controllers;

import com.phishing.DTO.UserProfileResponse;
import com.phishing.Entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

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
}