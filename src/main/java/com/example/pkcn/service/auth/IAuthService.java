package com.example.pkcn.service.auth;

import com.example.pkcn.controller.advice.cus_exception.*;
import com.example.pkcn.dto.request.ResetPasswordDTO;
import com.example.pkcn.dto.request.UserLoginDTO;
import com.example.pkcn.dto.request.UserLoginGoogleDTO;
import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.dto.response.JwtFromLoginDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;

public interface IAuthService {
    SuccessBasicDTO register(UserRegisterDTO user, String token) throws Exception;
    String createTokenResetPassword(String mail) throws UserNotExistException, DataStillValidException, IllegalUserStatusException;
    SuccessBasicDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception;
    SuccessBasicDTO verifyMail(String token) throws DataInvalidException;
    JwtFromLoginDTO loginGoogle(UserLoginGoogleDTO userLoginGoogleDTO) throws Exception;
    JwtFromLoginDTO loginLocal(UserLoginDTO userLoginDTO) throws Exception;
    SuccessBasicDTO isUserAdmin(String email) throws UserNotExistException;
}
