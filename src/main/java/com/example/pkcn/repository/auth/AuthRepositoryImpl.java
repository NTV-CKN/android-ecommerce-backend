package com.example.pkcn.repository.auth;

import com.example.pkcn.common.HashMD5Utils;
import com.example.pkcn.dto.request.UserRegisterDTO;
import com.example.pkcn.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("auth_repository_1")
@Transactional
public class AuthRepositoryImpl implements IAuthRepository{
    private EntityManager em;

    @Autowired
    public AuthRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public boolean register(UserRegisterDTO user) throws Exception {
        if((user.getEmail() == null || user.getEmail().isEmpty())
        || (user.getPassword() == null || user.getPassword().isEmpty()))
            return false;

        User userE = new User();
        userE.setTypeAccount(user.getTypeAccount());
        userE.setEmail(user.getEmail());
        userE.setPassword(HashMD5Utils.hashText(user.getPassword()));

        em.persist(userE);

        return userE.getId() != null;
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
}
