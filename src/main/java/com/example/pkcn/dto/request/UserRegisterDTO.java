package com.example.pkcn.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegisterDTO {
    private String email;
    private String password;
    @JsonProperty("type_account")
    private String typeAccount;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }
}
