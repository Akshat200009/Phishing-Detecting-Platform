package com.phishing.urlAnalysis.service;

import com.phishing.urlAnalysis.dto.SafeBrowsingResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class SafeBrowsingService {
    private final RestClient restClient;
    private final String apiKey;

    public SafeBrowsingService(
            @Value("${safe-browsing.api.url}") String apiUrl,
            @Value("${safe-browsing.api.key}") String apiKey
    ){
        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .build();
        this.apiKey = apiKey;
    }
    public SafeBrowsingResult checkUrl(String url){
        Map<String, Object> requestBody = Map.of(
                "client", Map.of(
                        "clientId", "phishing-detection-platform",
                        "clientVersion", "1.0"
                ),
                "threatInfo",  Map.of(
                        "threatTypes", List.of(
                                "MALWARE",
                                "SOCIAL_ENGINEERING",
                                "UNWANTED_SOFTWARE",
                                "POTENTIALLY_HARMFUL_APPLICATION"
                        ),
                        "platformTypes", List.of("ANY_PLATFORM"),
                        "threatEntryTypes", List.of("URL"),
                        "threatEntries", List.of(
                                Map.of("url", url)
                        )
                )
        );
        try {
            Map<?, ?> response = restClient.post()
                    .uri("/threatMatches:find")
                    .header("x-goog-api-key", apiKey)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(requestBody)
                    .retrieve()
                    .body(Map.class);

            if (response == null || !response.containsKey("matches")){
                return new SafeBrowsingResult(false, null);
            }
            Object matchesObject = response.get("matches");
            if (!(matchesObject instanceof List<?> matches) || matches.isEmpty()){
                return new SafeBrowsingResult(false, null);
            }
            Object firstMatch = matches.get(0);
            if (firstMatch instanceof Map<?,?> match){
                Object threatType = match.get("threatType");
                return new SafeBrowsingResult(true,
                        threatType != null ? threatType.toString() : "UNKNOWN");
            }
            return new SafeBrowsingResult(true, "UNKNOWN");
        } catch (Exception e) {
            System.err.println("Safe Browsing API error: " + e.getMessage());
            return new SafeBrowsingResult(false, "UNAVAILABLE");
        }
    }
}