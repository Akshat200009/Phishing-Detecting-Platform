package com.phishing.emailAnalysis.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UrlExtractionService {
    private static final Pattern URL_PATTERN = Pattern.compile(
            "https?://[^\\s<>\"']+",
            Pattern.CASE_INSENSITIVE
    );

    public List<String> extractUrls(String body){
        List<String> urls = new ArrayList<>();

        if (body == null || body.isBlank()){
            return urls;
        }
        Matcher matcher = URL_PATTERN.matcher(body);

        while(matcher.find()){
            urls.add(cleanUrl(matcher.group()));
        }
        return urls;
    }

    private String cleanUrl(String url){
        return url.replaceAll("[.,!?;:]+$", "");
    }
}