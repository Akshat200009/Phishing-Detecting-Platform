package com.phishing.urlAnalysis.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_scans")
public class UrlScan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 2048)
    private String url;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private LocalDateTime scannedAt;

    public UrlScan(){

    }

    public UrlScan(String url, String status){
        this.url = url;
        this.status = status;
        this.scannedAt = LocalDateTime.now();
    }

    public Long getId(){
        return id;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public LocalDateTime getScannedAt(){
        return scannedAt;
    }
    public void setScannedAt(LocalDateTime scannedAt){
        this.scannedAt = scannedAt;
    }
}
