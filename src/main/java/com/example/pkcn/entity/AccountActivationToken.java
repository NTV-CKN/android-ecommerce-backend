package com.example.pkcn.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "account_activation_tokens")
public class AccountActivationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private String token;
    private LocalDateTime expiredAt;
    private LocalDateTime usedAt;
    private Boolean isUsed;

    public AccountActivationToken() {}

    public AccountActivationToken(User user, String token) {
        initBaseAccountActivationToken(user, token);
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsedAt(LocalDateTime usedAt) {
        this.usedAt = usedAt;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    private void initBaseAccountActivationToken(User user, String token) {
        this.user = user;
        this.token = token;
        this.expiredAt = LocalDateTime.now().plusDays(1);
        this.isUsed = false;
    }
}
