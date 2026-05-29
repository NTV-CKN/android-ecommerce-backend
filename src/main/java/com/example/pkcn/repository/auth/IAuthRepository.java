package com.example.pkcn.repository.auth;

import com.example.pkcn.dto.request.UserRegisterDTO;

public interface IAuthRepository {
   public boolean register(UserRegisterDTO user) throws Exception;
   public boolean checkUserExistByMail(String email);
}
