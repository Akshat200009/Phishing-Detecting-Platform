package com.phishing.Services;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.phishing.DTO.LoginRequest;
import com.phishing.DTO.RegisterRequest;
import com.phishing.Entities.Role;
import com.phishing.Entities.User;
import com.phishing.Repositories.UserRepository;

@Service
public class AuthService {

	private final PasswordEncoder passenc;
	private final UserRepository userRepo;
	private final JwtService jwtService;
	
	public AuthService(PasswordEncoder passenc,UserRepository userRepo,JwtService jwtService)
	{
		this.passenc=passenc;
		this.userRepo=userRepo;
		this.jwtService=jwtService;
	}
	
	public User registerUser(RegisterRequest request)
	{
		if(userRepo.existsByEmail(request.getEmail()))
		{
			throw new IllegalArgumentException("Email already exists");
		}
		
		String hashedPassword = passenc.encode(request.getPassword());
		
		User user = new User();
		user.setEmail(request.getEmail());
		user.setName(request.getName());
		user.setPassword(hashedPassword);
		user.setRole(Role.USER);
		user.setCreatedAt(LocalDateTime.now());
		
		return userRepo.save(user);
	}
	
	public String loginUser(LoginRequest request) {

	    User user = userRepo
	            .findByEmail(request.getEmail())
	            .orElseThrow(() ->
	                    new IllegalArgumentException(
	                            "Invalid email or password"
	                    )
	            );

	    boolean passwordMatches =
	            passenc.matches(
	                    request.getPassword(),
	                    user.getPassword()
	            );

	    if (!passwordMatches) {
	        throw new IllegalArgumentException(
	                "Invalid email or password"
	        );
	    }

	    return jwtService.generateToken(
	            user.getEmail(),
	            user.getRole().name()
	    );
	}
}
