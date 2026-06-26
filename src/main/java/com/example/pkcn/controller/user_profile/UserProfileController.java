package com.example.pkcn.controller.user_profile;

import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.request.UserUpdateNameDTO;
import com.example.pkcn.dto.response.UserProfileDTO;
import com.example.pkcn.entity.User;
import com.example.pkcn.service.user.profile.IUserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/user-profile")
public class UserProfileController {

    private final IUserProfileService profileService;

    public UserProfileController(IUserProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<UserProfileDTO> getUserProfile(@AuthenticationPrincipal UserDetails userDetails) throws UserNotExistException {
        UserProfileDTO user = profileService.getUserProfile(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateFullName(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UserUpdateNameDTO req) throws UserNotExistException {
        String email = userDetails.getUsername();
        profileService.updateFullName(email, req.getFullName());
        return ResponseEntity.ok("Cập nhật thành công");
    }
}
