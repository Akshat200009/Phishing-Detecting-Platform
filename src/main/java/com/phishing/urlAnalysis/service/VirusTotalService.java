package com.phishing.urlAnalysis.service;

import com.phishing.urlAnalysis.dto.VirusTotalResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class VirusTotalService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${virustotal.api.key}")
    private String apiKey;

    @Value("${virustotal.api.url}")
    private String apiUrl;

    public VirusTotalService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restClient = RestClient.builder().build();
    }

    public VirusTotalResult scanUrl(String url) {

        try {

            // Step 1: Submit URL to VirusTotal
            MultiValueMap<String, String> formData =
                    new LinkedMultiValueMap<>();

            formData.add("url", url);

            String response = restClient.post()
                    .uri(apiUrl + "/urls")
                    .header("x-apikey", apiKey)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(String.class);

            JsonNode responseJson =
                    objectMapper.readTree(response);

            String analysisId = objectMapper.treeToValue(
                    responseJson
                            .path("data")
                            .path("id"),
                    String.class
            );

            if (analysisId == null || analysisId.isBlank()) {
                return VirusTotalResult.unavailable();
            }

            // Step 2: Poll analysis until completed
            JsonNode analysisJson = null;
            String status = null;

            for (int attempt = 0; attempt < 5; attempt++) {

                String analysisResponse = restClient.get()
                        .uri(apiUrl + "/analyses/" + analysisId)
                        .header("x-apikey", apiKey)
                        .retrieve()
                        .body(String.class);

                analysisJson =
                        objectMapper.readTree(analysisResponse);

                JsonNode attributes =
                        analysisJson
                                .path("data")
                                .path("attributes");

                status = objectMapper.treeToValue(
                        attributes.path("status"),
                        String.class
                );

                System.out.println(
                        "VirusTotal attempt "
                                + (attempt + 1)
                                + " - Status: "
                                + status
                );

                if ("completed".equalsIgnoreCase(status)) {
                    break;
                }

                // Wait 2 seconds before checking again
                Thread.sleep(2000);
            }

            // Step 3: If analysis is still not completed,
            // return the current status instead of treating it as safe
            if (!"completed".equalsIgnoreCase(status)) {

                return new VirusTotalResult(
                        status,
                        0,
                        0,
                        0
                );
            }

            // Step 4: Extract completed analysis statistics
            JsonNode attributes =
                    analysisJson
                            .path("data")
                            .path("attributes");

            JsonNode stats =
                    attributes.path("stats");

            int malicious = objectMapper.treeToValue(
                    stats.path("malicious"),
                    Integer.class
            );

            int suspicious = objectMapper.treeToValue(
                    stats.path("suspicious"),
                    Integer.class
            );

            int harmless = objectMapper.treeToValue(
                    stats.path("harmless"),
                    Integer.class
            );

            System.out.println("===== VirusTotal Result =====");
            System.out.println("Status: " + status);
            System.out.println("Malicious: " + malicious);
            System.out.println("Suspicious: " + suspicious);
            System.out.println("Harmless: " + harmless);
            System.out.println("=============================");

            return new VirusTotalResult(
                    status,
                    malicious,
                    suspicious,
                    harmless
            );

        } catch (Exception e) {

            System.err.println(
                    "VirusTotal API error: "
                            + e.getMessage()
            );

            return VirusTotalResult.unavailable();
        }
    }
}