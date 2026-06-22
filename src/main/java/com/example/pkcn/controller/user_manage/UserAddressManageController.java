package com.example.pkcn.controller.user_manage;

import com.example.pkcn.dto.response.user_manage.address.ShipFeeByAddressDTO;
import com.example.pkcn.dto.response.user_manage.address.UserAddressDTO;
import com.example.pkcn.entity.User;
import com.example.pkcn.repository.user.user_detail_repo.IUserRepository;
import com.example.pkcn.service.ship_fee_by_address.IShipFeeByAddressService;
import com.example.pkcn.service.user.address.IUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
 import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/user-manage-address")
@RestController
public class UserAddressManageController {
    private final IUserRepository userRepository;
    private final IUserAddressService userAddressService;

    @Autowired
    public UserAddressManageController(
            IUserRepository userRepository,
            IUserAddressService userAddressService
    ) {
        this.userRepository = userRepository;
        this.userAddressService = userAddressService;
    }

    @GetMapping("/view-addresses")
    public List<UserAddressDTO> getUserAddressListByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        return userAddressService.getUserAddressListByUserId(user.getId());
    }
}
