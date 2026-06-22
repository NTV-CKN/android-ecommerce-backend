package com.example.pkcn.service.auth;

import com.example.pkcn.common.AppUtils;
import com.example.pkcn.common.HashMD5Utils;
import com.example.pkcn.common.UserStatus;
import com.example.pkcn.controller.advice.cus_exception.*;
import com.example.pkcn.dto.request.ResetPasswordDTO;
import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;
import com.example.pkcn.entity.AccountActivationToken;
import com.example.pkcn.entity.User;
import com.example.pkcn.repository.auth.IAuthRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    //Tạo ra record user, tạo ra token để xác thực tài khoản
    public SuccessBasicDTO register(UserRegisterDTO user, String token) throws Exception {

        if ((user.getEmail() == null || user.getEmail().isEmpty())
                || (user.getPassword() == null || user.getPassword().isEmpty()))
            throw new IllegalArgumentException("Dữ liệu không hợp lệ");

        boolean isUserExist = authRepository.checkUserExistByMail(user.getEmail());
        if (isUserExist)
            throw new EmailAlreadyExistsException("Email đã tồn tại");

        if (!AppUtils.isStrongPassword(user.getPassword()))
            throw new IllegalFormatDataException(
                    "Mật khẩu yêu cầu lớn hơn 8 kí tự, có ít nhất 1 kí tự hoa, thường, số và kí tự đặc biệt"
            );

        if (!AppUtils.isFormatEmail(user.getEmail()))
            throw new IllegalFormatDataException(
                    "Email không đúng định dạng"
            );

        User userE = new User();
        userE.setTypeAccount(user.getTypeAccount());
        userE.setEmail(user.getEmail());
        userE.setPassword(HashMD5Utils.hashText(user.getPassword()));
        userE.setUserStatus(UserStatus.VERIFY_MAIL.getStatus());
        userE.setAccountActivationToken(
                new AccountActivationToken(userE, token)
        );

        boolean res = authRepository.register(userE);
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

    @Override
    @Transactional
    //Thực hiện lấy ra đối tượng AccountActivationToken để kiểm tra token hợp lệ
    public SuccessBasicDTO verifyMail(String token) throws DataInvalidException {
        AccountActivationToken accToken =
                authRepository.getAccountActivationTokenByToken(token);
        if (accToken == null
                || accToken.getExpiredAt().isBefore(LocalDateTime.now())
                || accToken.getUsed())
            throw new DataInvalidException(
                    "Token này không khả dụng hoặc đã hết hạn hay đã được ai đó sử dụng!"
            );

        accToken.getUser().setUserStatus(UserStatus.ACTIVE.getStatus());
        accToken.setUsedAt(LocalDateTime.now());
        accToken.setUsed(true);

        return new SuccessBasicDTO("Xác thực thành công!", true);
    }
}
