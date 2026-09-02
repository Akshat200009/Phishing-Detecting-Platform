package com.phishing.urlAnalysis.service;

import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class HttpsCheckService {
    public boolean isHttps(String url){
        try {
            URI uri = new URI(url);
            return "https".equalsIgnoreCase(uri.getScheme());
        }catch (Exception e){
            return false;
        }
    }
}
