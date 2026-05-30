package com.example.pkcn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_resets")
public class PasswordReset {
    @Id
    private String email;
    private String token;
    @Column(name = "expiry_date")
    private LocalDateTime expiryTime;
    @Column(name = "is_valid")
    private Boolean isValid;

    public PasswordReset(){}

    public PasswordReset(String email, String token, LocalDateTime expiryTime) {
        this.email = email;
        this.token = token;
        this.expiryTime = expiryTime;
        this.isValid = true;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpireTime() {
        return expiryTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expiryTime = expireTime;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }
}
