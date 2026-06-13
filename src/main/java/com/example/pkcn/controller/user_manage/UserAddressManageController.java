package com.example.pkcn.controller.user_manage;

import com.example.pkcn.dto.response.user_manage.address.ShipFeeByAddressDTO;
import com.example.pkcn.dto.response.user_manage.address.UserAddressDTO;
import com.example.pkcn.service.ship_fee_by_address.IShipFeeByAddressService;
import com.example.pkcn.service.user.address.IUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/v1/user-manage-address")
@RestController
public class UserAddressManageController {
    private final IUserAddressService userAddressService;

    @Autowired
    public UserAddressManageController(
            IUserAddressService userAddressService
    ) {
        this.userAddressService = userAddressService;
    }

    @GetMapping("/view-addresses")
    public List<UserAddressDTO> getUserAddressListByUserId(@RequestParam("userId") Integer userId) {
        return userAddressService.getUserAddressListByUserId(userId);
    }
}
