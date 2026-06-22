package com.example.pkcn.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<UserAddress> userAddresses;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private AccountActivationToken accountActivationToken;

    @Column(name = "type_account")
    private String typeAccount;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "status")
    private String userStatus;

    public Integer getId() {
        return id;
    }

    public Role getRole() {
        return role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getTypeAccount() {
        return typeAccount;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public void setTypeAccount(String typeAccount) {
        this.typeAccount = typeAccount;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public AccountActivationToken getAccountActivationToken() {
        return accountActivationToken;
    }

    public void setAccountActivationToken(AccountActivationToken accountActivationToken) {
        this.accountActivationToken = accountActivationToken;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
