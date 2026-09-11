package com.phishing.emailAnalysis.model;

import com.phishing.Entities.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "email_scans")
public class EmailScan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sender;
    private String subject;
    private int riskScore;
    private String status;
    private boolean suspiciousKeywords;
    private boolean urgencyDetected;
    private boolean credentialDetected;
    private LocalDateTime scannedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    protected EmailScan(){
        //required by JPA
    }
    public EmailScan(
            String sender,
            String subject,
            int riskScore,
            String status,
            boolean suspiciousKeywords,
            boolean urgencyDetected,
            boolean credentialDetected,
            User user){
        this.sender = sender;
        this.subject = subject;
        this.riskScore = riskScore;
        this.status = status;
        this.suspiciousKeywords = suspiciousKeywords;
        this.urgencyDetected = urgencyDetected;
        this.credentialDetected = credentialDetected;
        this.user = user;
        this.scannedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSuspiciousKeywords() {
        return suspiciousKeywords;
    }

    public boolean isUrgencyDetected() {
        return urgencyDetected;
    }

    public boolean isCredentialDetected() {
        return credentialDetected;
    }

    public LocalDateTime getScannedAt() {
        return scannedAt;
    }

    public User getUser() {
        return user;
    }
}
