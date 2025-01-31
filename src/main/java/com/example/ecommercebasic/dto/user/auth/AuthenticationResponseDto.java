package com.example.ecommercebasic.dto.user.auth;

public class AuthenticationResponseDto {
    private String accessToken;
    private String refreshToken;

    public AuthenticationResponseDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
