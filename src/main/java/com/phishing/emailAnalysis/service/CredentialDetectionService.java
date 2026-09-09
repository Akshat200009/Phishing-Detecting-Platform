package com.phishing.emailAnalysis.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class CredentialDetectionService {
    private final List<String> credentialKeywords = List.of(
            "password",
            "username",
            "login credentials",
            "account credentials",
            "verify your password",
            "enter your password",
            "confirm your identity",
            "security code",
            "otp",
            "one time password",
            "pin",
            "credit card"
    );
    public boolean containsCredentialRequest(
            String subject,
            String body
    ){
        String content = ((subject ==null ? "" : subject) + " "
        + (body == null ? "" : body))
                .toLowerCase(Locale.ROOT);

        return credentialKeywords.stream()
                .anyMatch(content::contains);
    }
}
