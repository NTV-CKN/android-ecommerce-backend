package com.example.pkcn.controller.auth;

import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;
import com.example.pkcn.service.auth.IAuthService;
import com.example.pkcn.service.mail.IMailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private IAuthService authService;
    private IMailService mailService;

    public AuthController(IAuthService authService, IMailService mailService) {
        this.authService = authService;
        this.mailService = mailService;
    }

    @PostMapping("/register")
    public SuccessBasicDTO register(@RequestBody UserRegisterDTO user) throws Exception {
        SuccessBasicDTO dto = authService.register(user);
        mailService.sendVerificationEmail(user.getEmail(), "");
        return dto;
    }
}
