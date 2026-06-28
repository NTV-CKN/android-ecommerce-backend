package com.example.pkcn.service.user.profile;

import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.response.user_manage.profile.UserProfileDTO;
import com.example.pkcn.entity.User;
import com.example.pkcn.repository.user.user_detail_repo.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileServiceImpl implements IUserProfileService {

    IUserRepository userRepository;

    public UserProfileServiceImpl(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserProfileDTO getUserProfile(String email) throws UserNotExistException {
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new UserNotExistException("Không tìm thấy user");
        }
        UserProfileDTO dto = new UserProfileDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setFullName(user.getFullName());
        dto.setTypeAccount(user.getTypeAccount());
        return dto;
    }

    @Transactional
    @Override
    public void updateFullName(String email, String fullName) throws UserNotExistException {
        if(fullName == null || fullName.trim().isEmpty()) {
            throw new RuntimeException("Họ và tên không được để trống!");
        }
        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new UserNotExistException("Không tìm thấy user");
        }
        user.setFullName(fullName);
        userRepository.updateFullName(user);
    }


}
