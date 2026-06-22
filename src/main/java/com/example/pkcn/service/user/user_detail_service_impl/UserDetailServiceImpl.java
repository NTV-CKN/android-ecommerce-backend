package com.example.pkcn.service.user.user_detail_service_impl;

import com.example.pkcn.common.UserStatus;
import com.example.pkcn.entity.User;
import com.example.pkcn.repository.user.user_detail_repo.IUserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Primary
public class UserDetailServiceImpl implements UserDetailsService {
    private IUserRepository userRepository;

    @Autowired
    public UserDetailServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if(user == null)
            throw new UsernameNotFoundException("Không tìm thấy người dùng với email: " + email);

        boolean isBanded = user.getUserStatus().equalsIgnoreCase(UserStatus.BANDED.getStatus());

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole().getNameRole())
                .accountLocked(isBanded)
                .build();
    }
}
