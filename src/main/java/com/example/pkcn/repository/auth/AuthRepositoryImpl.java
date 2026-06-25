package com.example.pkcn.repository.auth;

import com.example.pkcn.common.AppUtils;
import com.example.pkcn.common.HashMD5Utils;
import com.example.pkcn.common.UserStatus;
import com.example.pkcn.controller.advice.cus_exception.*;
import com.example.pkcn.dto.request.ResetPasswordDTO;
import com.example.pkcn.dto.request.UserLoginGoogleDTO;
import com.example.pkcn.entity.AccountActivationToken;
import com.example.pkcn.entity.PasswordReset;
import com.example.pkcn.entity.Role;
import com.example.pkcn.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository("auth_repository_1")
@Transactional
public class AuthRepositoryImpl implements IAuthRepository {
    private EntityManager em;

    @Autowired
    public AuthRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean register(User user) throws Exception {
        if ((user.getEmail() == null || user.getEmail().isEmpty())
                || (user.getPassword() == null || user.getPassword().isEmpty()))
            return false;

        if (user.getRole() == null) {
            Role defaultRole = em.getReference(Role.class, 2);
            user.setRole(defaultRole);
        }

        em.persist(user);

        return user.getId() != null;
    }

    @Override
    public boolean registerUserNoPassword(User user) {
        if ((user.getEmail() == null || user.getEmail().isEmpty()))
            return false;

        if (user.getRole() == null) {
            Role defaultRole = em.getReference(Role.class, 2);
            user.setRole(defaultRole);
        }

        em.persist(user);

        return user.getId() != null;
    }


    @Override
    public boolean checkUserExistByMail(String email) {
        String sql = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        query.setParameter("email", email);
        List<User> results = query.getResultList();
        User user = results.isEmpty() ? null : results.getFirst();
        return user != null;
    }

    @Override
    public String createTokenResetPassword(String email) throws DataStillValidException, UserNotExistException, IllegalUserStatusException {
        PasswordReset passwordReset = em.find(PasswordReset.class, email);
        if (passwordReset != null) {
            if (passwordReset.getValid() && passwordReset.getExpireTime().isAfter(LocalDateTime.now()))
                throw new DataStillValidException(
                        "Token reset mật khẩu của email này vẫn còn hiệu lực"
                );
            em.remove(passwordReset);
        }

        LocalDateTime current = LocalDateTime.now();
        LocalDateTime currentPlus10min = current.plusMinutes(10);

        String token = UUID.randomUUID().toString();
        PasswordReset passwordResetNew = new PasswordReset(
                email,
                token,
                currentPlus10min
        );

        em.persist(passwordResetNew);
        return token;
    }

    @Override
    public boolean resetPassword(ResetPasswordDTO resetPasswordDTO) throws Exception {
        PasswordReset passwordReset = em.find(PasswordReset.class, resetPasswordDTO.getEmail());
        if (passwordReset == null)
            throw new UserNotExistException(
                    "Không tìm thấy người dùng có email này trong bảng khôi phục mật khẩu"
            );
        if (!passwordReset.getToken().equals(resetPasswordDTO.getToken()))
            throw new IllegalArgumentException("Token khôi phục mật khẩu không khớp");

        if (passwordReset.getValid() && LocalDateTime.now().isAfter(passwordReset.getExpireTime()))
            throw new DataInvalidException(
                    "Token này đã hết hạn, vui lòng gửi lại yêu cầu thiết lập mật khẩu mới"
            );

        if (!AppUtils.isStrongPassword(resetPasswordDTO.getPassword()))
            throw new IllegalFormatDataException(
                    "Mật khẩu yêu cầu lớn hơn 8 kí tự, có ít nhất 1 kí tự hoa, thường, số và kí tự đặc biệt"
            );

        String hashPassword = HashMD5Utils.hashText(resetPasswordDTO.getPassword());
        String sql = """
                UPDATE User u
                SET u.password = :hashPassword
                WHERE u.email = :email
                """;
        boolean res = em.createQuery(sql)
                .setParameter("email", resetPasswordDTO.getEmail())
                .setParameter("hashPassword", hashPassword)
                .executeUpdate() > 0;
        if (!res) throw new Exception("Không thể khôi phục mật khẩu");

        em.remove(passwordReset);

        return true;
    }

    @Override
    public boolean checkUserExistAndActiveByEmail(String email) throws IllegalUserStatusException, UserNotExistException {
        String sqlFindUserByEmail = "SELECT u FROM User u WHERE u.email = :email";
        TypedQuery<User> query = em.createQuery(sqlFindUserByEmail, User.class);
        query.setParameter("email", email);
        User user = query.getSingleResultOrNull();
        if(user == null)
            throw new UserNotExistException("Người dùng không tồn tại");

        if(!user.getUserStatus().equals(UserStatus.ACTIVE.getStatus()))
            throw new IllegalUserStatusException("Tài khoản không ở trạng thái hoạt động");

        return true;
    }

    @Override
    //Hàm này trả về đối tượng AccountActivationToken dùng cho việc kiểm tra hạn xác thực,
    //fetch User để thay đổi trạng thái nếu xác thực thành công,...
    public AccountActivationToken getAccountActivationTokenByToken(String token) {
        String sql = """
                SELECT a
                FROM AccountActivationToken a
                JOIN FETCH a.user
                WHERE a.token = :token
                """;
        return em.createQuery(sql, AccountActivationToken.class)
                .setParameter("token", token)
                .getSingleResultOrNull();
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = """
                SELECT u
                FROM User u
                WHERE u.email = :email
                """;

        return em.createQuery(sql, User.class)
                .setParameter("email", email)
                .getSingleResultOrNull();
    }
}
