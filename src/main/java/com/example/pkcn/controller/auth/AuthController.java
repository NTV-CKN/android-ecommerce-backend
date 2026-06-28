package com.example.pkcn.controller.auth;

import com.example.pkcn.common.JwtUtils;
import com.example.pkcn.controller.advice.cus_exception.*;
import com.example.pkcn.dto.request.*;
import com.example.pkcn.dto.response.JwtFromLoginDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;
import com.example.pkcn.service.auth.IAuthService;
import com.example.pkcn.service.mail.IMailService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private IAuthService authService;
    private IMailService mailService;
    private JwtUtils jwtUtils;

    public AuthController(IAuthService authService, IMailService mailService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.mailService = mailService;
        this.jwtUtils = jwtUtils;
    }

    //End point này xử lí đăng nhập google, cập nhật lại avatar và fullName
    //Kiểm tra nếu email có tồn tại nhưng phải thuộc Google mới chấp nhận
    @PostMapping("/login-google")
    public JwtFromLoginDTO loginGoogle(@RequestBody UserLoginGoogleDTO userLoginGoogleDTO) throws Exception {
        return authService.loginGoogle(userLoginGoogleDTO);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenDTO refreshTokenDTO) {
        if (refreshTokenDTO == null || refreshTokenDTO.getRefreshToken().isEmpty())
            throw new IllegalArgumentException("Tham số không hợp lệ");
        try {
        if (jwtUtils.validateToken(refreshTokenDTO.getRefreshToken())) {
            String email = jwtUtils.getEmailFromToken(refreshTokenDTO.getRefreshToken());
            String newAccessToken = jwtUtils.generateAccessToken(email);

            System.out.println(newAccessToken);

            JwtFromLoginDTO response = new JwtFromLoginDTO(
                    newAccessToken,
                    refreshTokenDTO.getRefreshToken(),
                    null, null, null
            );
            return ResponseEntity.ok(response);
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Refresh token không hợp lệ");
        } catch (ExpiredJwtException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("REFRESH_TOKEN_EXPIRED");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mã xác thực không hợp lệ");
        }
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

    @PostMapping("/check-role-admin")
    public ResponseEntity<SuccessBasicDTO> isUserAdmin(@RequestHeader("Authorization") String token) throws UserNotExistException {
        if(token == null)
             return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED)
                     .body(
                             new SuccessBasicDTO("Vui lòng đăng nhập", false)
                     );

        token = token.substring(7);
        String email = jwtUtils.getEmailFromToken(token);

        return ResponseEntity.ok(authService.isUserAdmin(email));
    }
}
