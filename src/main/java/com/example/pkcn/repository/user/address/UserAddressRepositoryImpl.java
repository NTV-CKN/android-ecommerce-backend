package com.example.pkcn.repository.user.address;

import com.example.pkcn.dto.response.user_manage.address.UserAddressDTO;
import com.example.pkcn.entity.User;
import com.example.pkcn.entity.UserAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class UserAddressRepositoryImpl implements IUserAddressRepository {
    private final EntityManager em;

    @Autowired
    public UserAddressRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public List<UserAddress> getUserAddressListByUserId(Integer userId) {
        String sql = """
                SELECT ud
                FROM UserAddress ud
                JOIN ud.user u
                WHERE u.id = :userId
                """;
        TypedQuery<UserAddress> query = em.createQuery(sql, UserAddress.class);
        query.setParameter("userId", userId);

        return query.getResultList();
    }

    @Override
    public User getUserByEmailAndFetchAddresses(String email) {
        String sql = """
                SELECT u
                FROM User u
                LEFT JOIN FETCH u.userAddresses
                WHERE u.email = :email
                """;
        TypedQuery<User> query = em.createQuery(sql, User.class);
        query.setParameter("email", email);

        return query.getSingleResultOrNull();
    }
}
