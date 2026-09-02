package com.phishing.urlAnalysis.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class KeywordDetectionService {
    private final List<String> suspiciousKeywords = List.of(
            "login",
            "signin",
            "verify",
            "verification",
            "account",
            "update",
            "secure",
            "security",
            "password",
            "credential",
            "confirm",
            "bank",
            "wallet",
            "payment",
            "invoice",
            "reset",
            "authenticate",
            "authorization"
    );

    public boolean containsSuspiciousKeyword(String url){
        if (url == null || url.isBlank()){
            return false;
        }
        String normalizedUrl = url.toLowerCase(Locale.ROOT);
        return suspiciousKeywords.stream().anyMatch(normalizedUrl::contains);
    }
}
