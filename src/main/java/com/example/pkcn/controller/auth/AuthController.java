package com.example.pkcn.controller.auth;

import com.example.pkcn.controller.advice.cus_exception.EmailAlreadyExistsException;
import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;
import com.example.pkcn.service.auth.IAuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

@RequestMapping("/api/v1/auth")
@RestController
public class AuthController {
    private IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public SuccessBasicDTO register(@RequestBody UserRegisterDTO user) throws Exception {
        return authService.register(user);
    }
}
