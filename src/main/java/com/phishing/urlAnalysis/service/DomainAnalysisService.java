package com.phishing.urlAnalysis.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.regex.Pattern;

@Service
public class DomainAnalysisService {
    private static final Pattern IPV4_PATTERN = Pattern.compile(
            "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
                    "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$"
    );
    public String extractDomain(String url){
        try{
            URI uri = new URI(url);
            return uri.getHost();
        }catch (Exception e){
            return null;
        }
    }
    public boolean isIpAddress(String url){
        String domain = extractDomain(url);

        if (domain == null){
            return false;
        }
        return IPV4_PATTERN.matcher(domain).matches();
    }
    public boolean hasSuspiciousDomain(String url){
        String domain = extractDomain(url);

        if (domain.length() > 50){
            return true;
        }
        long hyphenCount = domain.chars()
                .filter(character -> character == '-')
                .count();

        if (hyphenCount >= 3){
            return true;
        }
        return isIpAddress(url);
    }
}
