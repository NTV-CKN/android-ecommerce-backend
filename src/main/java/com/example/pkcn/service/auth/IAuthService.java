package com.example.pkcn.service.auth;

import com.example.pkcn.controller.advice.cus_exception.DataStillValidException;
import com.example.pkcn.controller.advice.cus_exception.EmailAlreadyExistsException;
import com.example.pkcn.controller.advice.cus_exception.IllegalUserStatusException;
import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.request.ResetPasswordDTO;
import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;

public interface IAuthService {
    SuccessBasicDTO register(UserRegisterDTO user) throws Exception;
    SuccessBasicDTO verifyMail(String mail) throws EmailAlreadyExistsException, IllegalUserStatusException, UserNotExistException;
    String createTokenResetPassword(String mail) throws UserNotExistException, DataStillValidException;
    SuccessBasicDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception;
}
