package com.example.pkcn.controller.auth;

import com.example.pkcn.controller.advice.cus_exception.*;
import com.example.pkcn.dto.request.ResetPasswordDTO;
import com.example.pkcn.dto.request.UserLoginDTO;
import com.example.pkcn.dto.request.UserLoginGoogleDTO;
import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.dto.response.JwtFromLoginDTO;
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

    //End point này xử lí đăng nhập google, cập nhật lại avatar và fullName
    //Kiểm tra nếu email có tồn tại nhưng phải thuộc Google mới chấp nhận
    @PostMapping("/login-google")
    public JwtFromLoginDTO loginGoogle(@RequestBody UserLoginGoogleDTO userLoginGoogleDTO) throws Exception {
        return authService.loginGoogle(userLoginGoogleDTO);
    }

    //End point này xử lí đăng nhập tài khoản thông thường, nếu tài khoản đã tồn tại
    //và type_account là LOCAL thì tạo jwt và đăng nhập
    @PostMapping("/login-local")
    public JwtFromLoginDTO loginLocal(@RequestBody UserLoginDTO userLoginDTO) throws Exception {
        return authService.loginLocal(userLoginDTO);
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
