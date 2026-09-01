package com.phishing.urlAnalysis.DTO;

import jakarta.validation.constraints.NotBlank;

public class UrlScanRequest {
    @NotBlank(message = "URL cannot be empty")
    private String url;

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }
}
