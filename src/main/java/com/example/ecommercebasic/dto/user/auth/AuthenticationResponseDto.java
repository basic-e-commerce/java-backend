package com.example.ecommercebasic.dto.user.auth;

public class AuthenticationResponseDto {
    private String accessToken;
    private String firstName;
    private String lastName;
    private String username;

    public AuthenticationResponseDto(String accessToken, String firstName, String lastName, String username) {
        this.accessToken = accessToken;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;

    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUsername() {
        return username;
    }

    public String getLastName() {
        return lastName;
    }
}
