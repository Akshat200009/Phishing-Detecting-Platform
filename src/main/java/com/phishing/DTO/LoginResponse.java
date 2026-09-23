package com.phishing.DTO;

public class LoginResponse {

    private String token;
    private String type;
    private String role;

    public LoginResponse(){
    }

    public LoginResponse(String token, String type, String role) {
        this.token = token;
        this.type = type;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
