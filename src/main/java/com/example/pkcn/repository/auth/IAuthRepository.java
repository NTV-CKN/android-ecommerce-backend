package com.example.pkcn.repository.auth;

import com.example.pkcn.controller.advice.cus_exception.IllegalUserStatusException;
import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.request.UserRegisterDTO;

public interface IAuthRepository {
   public boolean register(UserRegisterDTO user) throws Exception;
   public boolean checkUserExistByMail(String email);
   public boolean verifyMail(String email) throws UserNotExistException, IllegalUserStatusException;
}
