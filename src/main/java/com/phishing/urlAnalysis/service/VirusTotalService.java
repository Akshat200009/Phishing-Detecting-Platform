package com.phishing.urlAnalysis.service;

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

    public VirusTotalService(ObjectMapper objectMapper){
        this.objectMapper = objectMapper;
        this.restClient = RestClient.builder().build();
    }

    public VirusTotalResult scanUrl(String url){
        try{
            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("url", url);

            String response = restClient.post()
                    .uri(apiUrl + "/urls")
                    .header("x-apikey", apiKey)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(formData)
                    .retrieve()
                    .body(String.class);

            JsonNode responseJson = objectMapper.readTree(response);

            String analysisId = objectMapper.treeToValue(
                    responseJson.path("data").path("id"),
                    String.class
            );

            if (analysisId == null || analysisId.isBlank()){
                return VirusTotalResult.unavailable();
            }
            // retrieves analysis result
            String analysisResponse = restClient.get()
                    .uri(apiUrl + "/analyses/" + analysisId)
                    .header("x-apikey", apiKey)
                    .retrieve()
                    .body(String.class);

            JsonNode analysisJson =
                    objectMapper.readTree(analysisResponse);

            JsonNode attributes =
                    analysisJson.path("data").path("attributes");

            JsonNode stats =
                    attributes.path("stats");

            String status = objectMapper.treeToValue(
                    attributes.path("status"),
                    String.class
            );

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

            return new VirusTotalResult(
                    status,
                    malicious,
                    suspicious,
                    harmless
            );
        } catch (Exception e) {
            return VirusTotalResult.unavailable();
        }
    }
    public record VirusTotalResult(
            String status,
            int malicious,
            int suspicious,
            int harmless
    ){
        public boolean isMalicious(){
            return malicious > 0;
        }

        public boolean isSuspicious(){
            return  suspicious > 0;
        }

        public boolean isCompleted(){
            return "completed".equalsIgnoreCase(status);
        }

        public static VirusTotalResult unavailable(){
            return  new VirusTotalResult(
                    "unavailable",
                    0,
                    0,
                    0
            );
        }
    }
}
