package com.employee.management.dto;

public class AuthResponse {
    private final String accessToken;
    private final String tokenType = "Bearer";
    private final long expiresInSeconds;
    private final String role;

    public AuthResponse(String accessToken, long expiresInSeconds, String role) {
        this.accessToken = accessToken;
        this.expiresInSeconds = expiresInSeconds;
        this.role = role;
    }

    public String getAccessToken() { return accessToken; }
    public String getTokenType() { return tokenType; }
    public long getExpiresInSeconds() { return expiresInSeconds; }
    public String getRole() { return role; }
}
