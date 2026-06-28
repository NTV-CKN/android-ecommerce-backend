package com.example.pkcn.service.user.profile;

import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.response.user_manage.profile.UserProfileDTO;

public interface IUserProfileService {
    UserProfileDTO getUserProfile(String email) throws UserNotExistException;
    void updateFullName(String email, String fullName) throws UserNotExistException;
}
