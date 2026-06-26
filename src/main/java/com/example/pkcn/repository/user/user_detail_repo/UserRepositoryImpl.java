package com.example.pkcn.repository.user.user_detail_repo;

import com.example.pkcn.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
@Primary
public class UserRepositoryImpl implements IUserRepository {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User findUserByEmail(String email) {
        String sql = """
                SELECT u
                FROM User u
                WHERE u.email = :email
                """;

        return em.createQuery(sql, User.class)
                .setParameter("email", email)
                .getSingleResultOrNull();
    }

    @Override
    public void updateFullName(User user) {
        em.merge(user);
    }


}
