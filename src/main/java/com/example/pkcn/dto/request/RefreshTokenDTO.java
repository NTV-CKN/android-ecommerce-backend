package com.example.pkcn.dto.request;


import com.fasterxml.jackson.annotation.JsonProperty;

public class RefreshTokenDTO {
    @JsonProperty("refresh_token")
    private String refreshToken;

    public RefreshTokenDTO(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() { return refreshToken; }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}