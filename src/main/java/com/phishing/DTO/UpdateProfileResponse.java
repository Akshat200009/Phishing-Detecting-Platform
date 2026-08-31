package com.phishing.DTO;

public class UpdateProfileResponse {

    private UserProfileResponse user;
    private String token;

    public UpdateProfileResponse() {
    }

    public UpdateProfileResponse(
            UserProfileResponse user,
            String token) {

        this.user = user;
        this.token = token;
    }

    public UserProfileResponse getUser() {
        return user;
    }

    public void setUser(UserProfileResponse user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}