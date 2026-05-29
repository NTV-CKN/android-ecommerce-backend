package com.example.pkcn.service.mail;

public interface IMailService {
    void sendVerificationEmail(String toEmail, String deepLink);
}