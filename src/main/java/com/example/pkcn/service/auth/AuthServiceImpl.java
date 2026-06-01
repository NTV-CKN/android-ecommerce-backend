package com.example.pkcn.service.auth;

import com.example.pkcn.controller.advice.cus_exception.DataStillValidException;
import com.example.pkcn.controller.advice.cus_exception.EmailAlreadyExistsException;
import com.example.pkcn.controller.advice.cus_exception.IllegalUserStatusException;
import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.request.ResetPasswordDTO;
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

    @Override
    public SuccessBasicDTO verifyMail(String mail) throws EmailAlreadyExistsException, IllegalUserStatusException, UserNotExistException {
        if ((mail == null || mail.isEmpty()))
            throw new IllegalArgumentException("Dữ liệu không hợp lệ");

        boolean isUserExist = authRepository.checkUserExistByMail(mail);
        if (!isUserExist)
            throw new UserNotExistException("Không có người dùng nào chứa email này");

        boolean res = authRepository.verifyMail(mail);
        if (res)
            return new SuccessBasicDTO(
                    "Xác thực thành công",
                    true
            );
        else
            return new SuccessBasicDTO(
                    "Xác thực thất bại",
                    false
            );
    }

    @Override
    public String createTokenResetPassword(String mail) throws UserNotExistException, DataStillValidException, IllegalUserStatusException {
        boolean isUserExistAndActive = authRepository.checkUserExistAndActiveByEmail(mail);

        if (!isUserExistAndActive)
            throw new UserNotExistException("Không tồn tại người dùng nào có email này hoặc trạng thái không hợp lệ");

        return authRepository.createTokenResetPassword(mail);
    }

    @Override
    public SuccessBasicDTO resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception {
        boolean isUserExistAndActive = authRepository.checkUserExistAndActiveByEmail(resetPasswordDTO.getEmail());
        if (!isUserExistAndActive)
            throw new UserNotExistException("Không tồn tại người dùng nào có email này hoặc trạng thái không hợp lệ");

        boolean res = authRepository.resetPassword(resetPasswordDTO);
        if (!res)
            throw new Exception("Khôi phục mật khẩu thất bại");

        return new SuccessBasicDTO(
                "Khôi phục mật khẩu thành công",
                true
        );
    }
}
