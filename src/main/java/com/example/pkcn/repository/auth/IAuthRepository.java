package com.example.pkcn.repository.auth;

import com.example.pkcn.controller.advice.cus_exception.DataStillValidException;
import com.example.pkcn.controller.advice.cus_exception.IllegalUserStatusException;
import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.request.ResetPasswordDTO;
import com.example.pkcn.entity.AccountActivationToken;
import com.example.pkcn.entity.User;


public interface IAuthRepository {
    boolean register(User user) throws Exception;
    boolean registerUserNoPassword(User user);
    //Phương thức này tạo record user nhưng set status đợi xác thực từ người dùng
    boolean checkUserExistByMail(String email);
    String createTokenResetPassword(String email) throws DataStillValidException, UserNotExistException, IllegalUserStatusException;
    boolean resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception;
    boolean checkUserExistAndActiveByEmail(String email) throws IllegalUserStatusException, UserNotExistException;
    AccountActivationToken getAccountActivationTokenByToken(String token);
    User getUserByEmail(String email);
    boolean isUserAdmin(String email) throws UserNotExistException;
}
