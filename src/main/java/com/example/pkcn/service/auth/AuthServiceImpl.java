package com.example.pkcn.service.auth;

import com.example.pkcn.controller.advice.cus_exception.EmailAlreadyExistsException;
import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;
import com.example.pkcn.repository.auth.IAuthRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class AuthServiceImpl implements IAuthService {
    private IAuthRepository authRepository;

    public AuthServiceImpl(
            @Qualifier("auth_repository_1")
            IAuthRepository authRepository
    ) {
        this.authRepository = authRepository;
    }


    @Override
    public SuccessBasicDTO register(UserRegisterDTO user) throws Exception {

        if ((user.getEmail() == null || user.getEmail().isEmpty())
                || (user.getPassword() == null || user.getPassword().isEmpty()))
            throw new IllegalArgumentException("Dữ liệu không hợp lệ");

        boolean isUserExist = authRepository.checkUserExistByMail(user.getEmail());
        if (isUserExist)
            throw new EmailAlreadyExistsException("Email đã tồn tại");

        boolean res = authRepository.register(user);
        if (res) {
            return new SuccessBasicDTO(
                    "Đăng kí tài khoản thành công",
                    true
            );
        }
        return new SuccessBasicDTO(
                "Lỗi không thể đăng kí tài khoản",
                false
        );
    }
}
