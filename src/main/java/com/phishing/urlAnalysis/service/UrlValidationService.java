package com.phishing.urlAnalysis.service;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;

@Service
public class UrlValidationService {
    public boolean isValidUrl(String url){
        if (url == null || url.isBlank()){
            return false;
        }
        try {
            URI uri = new URI(url);
            return uri.getScheme() != null && uri.getHost() != null;
        } catch (URISyntaxException e){
            return false;
        }
    }
}