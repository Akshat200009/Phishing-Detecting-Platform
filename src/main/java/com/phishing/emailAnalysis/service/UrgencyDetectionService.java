package com.phishing.emailAnalysis.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class UrgencyDetectionService {
    private final List<String> urgencyKeywords = List.of(
            "urgent",
            "immediately",
            "act now",
            "action required",
            "last warning",
            "final warning",
            "final notice",
            "expires today",
            "expires soon",
            "within 24 hours",
            "within 48 hours",
            "account will be closed",
            "account will be suspended",
            "account will be deleted",
            "respond immediately"
    );
    public boolean containsUrgency(
            String subject,
            String body
    ){
        String content = ((subject == null ? "" : subject) + " "
        +  (body == null ? "" : body))
                .toLowerCase(Locale.ROOT);

        return urgencyKeywords.stream()
                .anyMatch(content::contains);
    }
}