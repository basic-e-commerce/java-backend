package com.example.ecommercebasic.dto.user.auth;

public class AuthenticationResponseDto {
    private String accessToken;
    private String name;
    private String username;
    private String phoneNo;

    public AuthenticationResponseDto(String accessToken, String name, String username, String phoneNo) {
        this.accessToken = accessToken;
        this.name = name;
        this.username = username;
        this.phoneNo = phoneNo;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }
}
