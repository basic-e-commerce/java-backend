package com.example.ecommercebasic.dto.user.auth;

public class AuthenticationResponseDto {
    private String accessToken;

    public AuthenticationResponseDto(String accessToken) {
        this.accessToken = accessToken;

    }

    public String getAccessToken() {
        return accessToken;
    }


}
