package com.phishing.Exception;

public class ScanNotFoundException extends RuntimeException{
    public ScanNotFoundException(String message){
        super(message);
    }
}
