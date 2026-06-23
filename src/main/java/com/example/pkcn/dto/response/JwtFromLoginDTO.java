package com.example.pkcn.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtFromLoginDTO {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
    private String avatar;
    @JsonProperty("full_name")
    private String fullName;
    @JsonProperty("user_id")
    private Integer userId;

    public JwtFromLoginDTO() {
    }

    public JwtFromLoginDTO(String accessToken, String refreshToken,
                           String avatar, String fullName, Integer userId
    ) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.fullName = fullName;
        this.avatar = avatar;
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public Integer getUserId() {
        return userId;
    }
}
