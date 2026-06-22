package com.example.pkcn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtFromLoginDTO {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;

    public JwtFromLoginDTO() {
    }

    public JwtFromLoginDTO(String accessToken, String refreshToken) {
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
