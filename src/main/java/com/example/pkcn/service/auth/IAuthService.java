package com.example.pkcn.service.auth;

import com.example.pkcn.controller.advice.cus_exception.EmailAlreadyExistsException;
import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;

public interface IAuthService {
    public SuccessBasicDTO register(UserRegisterDTO user) throws Exception;
}
