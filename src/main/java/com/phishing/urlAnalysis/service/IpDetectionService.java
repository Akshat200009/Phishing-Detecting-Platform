package com.phishing.urlAnalysis.service;

import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.URI;

@Service
public class IpDetectionService {
    public boolean isIpAddress(String url){
        try {
            URI uri = new URI(url);
            String host = uri.getHost();

            if (host == null){
                return false;
            }
            InetAddress address =  InetAddress.getByName(host);
            return address.getHostAddress().equals(host);
        } catch (Exception e){
            return false;
        }
    }
    public String resolveIpAddress(String url){
        try {
            URI uri = new URI(url);
            String host = uri.getHost();

            if (host == null){
                return null;
            }
            InetAddress address = InetAddress.getByName(host);
            return address.getHostAddress();
        } catch (Exception e) {
            return null;
        }
    }
}