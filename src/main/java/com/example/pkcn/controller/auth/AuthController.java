package com.example.pkcn.controller.auth;

import com.example.pkcn.controller.advice.cus_exception.*;
import com.example.pkcn.dto.request.ResetPasswordDTO;
import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;
import com.example.pkcn.service.auth.IAuthService;
import com.example.pkcn.service.mail.IMailService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.UUID;

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
        String token = UUID.randomUUID().toString();
        SuccessBasicDTO dto = authService.register(user, token);

        String deepLink = "myapp://auth/verify-mail?token=" + token + "&email=" + user.getEmail();
        mailService.sendVerificationEmail(user.getEmail(), deepLink);
        return dto;
    }

    @PostMapping("reset-password")
    public SuccessBasicDTO resetPassword(@RequestBody ResetPasswordDTO resetPassword) throws Exception {
        return authService.resetPassword(resetPassword);
    }

    @GetMapping("/verify-mail")
    public SuccessBasicDTO verifyMail(@RequestParam("token") String token) throws IllegalUserStatusException, EmailAlreadyExistsException, UserNotExistException, DataInvalidException {
        return authService.verifyMail(token);
    }

    @GetMapping("/send-email-reset-password")
    public SuccessBasicDTO sendMailResetPassword(@RequestParam("email") String email) throws Exception {
        String token = authService.createTokenResetPassword(email);
        if (token == null)
            throw new Exception("Không thể tạo token");

        String deepLink = "myapp://auth/reset-password?token=" + token + "&email=" + email;
        mailService.sendResetPasswordEmail(email, deepLink);

        return new SuccessBasicDTO(
                "Thành công! Chúng tôi đã gửi mail để reset lại mật khẩu cho bạn",
                true
        );
    }
}
