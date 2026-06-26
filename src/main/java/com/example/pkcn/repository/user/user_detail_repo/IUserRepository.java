package com.example.pkcn.repository.user.user_detail_repo;

import com.example.pkcn.entity.User;

public interface IUserRepository {
    User findUserByEmail(String email);
    void updateFullName(User user);
}
