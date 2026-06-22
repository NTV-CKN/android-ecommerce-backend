package com.example.pkcn.service.auth;

import com.example.pkcn.controller.advice.cus_exception.*;
import com.example.pkcn.dto.request.ResetPasswordDTO;
import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;

public interface IAuthService {
    SuccessBasicDTO register(UserRegisterDTO user, String token) throws Exception;
    String createTokenResetPassword(String mail) throws UserNotExistException, DataStillValidException, IllegalUserStatusException;
    SuccessBasicDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception;
    SuccessBasicDTO verifyMail(String token) throws DataInvalidException;
}
