package com.gym.api.entity.response;

public class AuthenticationResponse {

    private String token;

    private String preferredLanguage;

    private String preferredLayout;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token, String preferredLanguage, String preferredLayout) {
        this.token = token;
        this.preferredLanguage = preferredLanguage;
        this.preferredLayout = preferredLayout;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getPreferredLayout() {
        return preferredLayout;
    }

    public void setPreferredLayout(String preferredLayout) {
        this.preferredLayout = preferredLayout;
    }
}
