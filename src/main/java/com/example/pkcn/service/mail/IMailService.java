package com.example.pkcn.service.mail;

import org.springframework.scheduling.annotation.Async;

public interface IMailService {
    void sendVerificationEmail(String toEmail, String deepLink);

    @Async
    void sendResetPasswordEmail(String toEmail, String deepLink);
}