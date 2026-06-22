package com.example.pkcn.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserLoginGoogleDTO {
    @JsonProperty("full_name")
    private String fullName;
    private String avatar;
    private String email;

    public UserLoginGoogleDTO() {
    }

    public UserLoginGoogleDTO(String fullName, String avatar, String email) {
        this.fullName = fullName;
        this.avatar = avatar;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getEmail() {
        return email;
    }
}
