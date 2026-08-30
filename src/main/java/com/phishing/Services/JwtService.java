package com.phishing.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {

	private static final String SECRET_KEY = "phishingDetectionProjectSecretKey2026ForJwtTokenGeneration";

	private static final long EXPIRATION_TIME = 30L * 24 * 60 * 60 * 1000;

	private SecretKey getSigningKey() {

		return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String email, String role) {

		Date issuedAt = new Date();

		Date expirationDate = new Date(issuedAt.getTime() + EXPIRATION_TIME);

		return Jwts.builder().subject(email).claim("role", role).issuedAt(issuedAt).expiration(expirationDate)
				.signWith(getSigningKey()).compact();
	}

	public String extractEmail(String token) {

		Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();

		return claims.getSubject();
	}
}