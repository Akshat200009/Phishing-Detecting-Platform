package com.phishing.emailAnalysis.dto;

import jakarta.validation.constraints.NotBlank;

public class EmailAnalysisRequest {

    @NotBlank(message = "Sender is required")
    private String sender;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Email body is required")
    private String body;

    public EmailAnalysisRequest(){
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}