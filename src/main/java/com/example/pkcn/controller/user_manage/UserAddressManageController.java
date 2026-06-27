package com.example.pkcn.controller.user_manage;

import com.example.pkcn.dto.request.AddUserAddressDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;
import com.example.pkcn.dto.response.user_manage.address.ShipFeeByAddressDTO;
import com.example.pkcn.dto.response.user_manage.address.UserAddressDTO;
import com.example.pkcn.entity.User;
import com.example.pkcn.repository.user.user_detail_repo.IUserRepository;
import com.example.pkcn.service.ship_fee_by_address.IShipFeeByAddressService;
import com.example.pkcn.service.user.address.IUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
 import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/user-manage-address")
@RestController
public class UserAddressManageController {
    private final IUserRepository userRepository;
    private final IUserAddressService userAddressService;
    private final IShipFeeByAddressService shipFeeByAddressService;

    @Autowired
    public UserAddressManageController(
            IUserRepository userRepository,
            IUserAddressService userAddressService,
            IShipFeeByAddressService shipFeeByAddressService
    ) {
        this.userRepository = userRepository;
        this.userAddressService = userAddressService;
        this.shipFeeByAddressService = shipFeeByAddressService;
    }

    @GetMapping("/view-addresses")
    public List<UserAddressDTO> getUserAddressListByUserId(@AuthenticationPrincipal UserDetails userDetails) {
        System.out.println(userDetails);

        User user = userRepository.findUserByEmail(userDetails.getUsername());
        return userAddressService.getUserAddressListByUserId(user.getId());
    }

    @PostMapping("/add-address")
    public SuccessBasicDTO addUserAddress(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddUserAddressDTO addUserAddressDTO
    ) throws Exception {
        if(!shipFeeByAddressService.checkMatchProvinceCity(addUserAddressDTO.getProvinceCity()))
            return new SuccessBasicDTO(
                    "Tỉnh/Thành phố không tìm thấy trong dữ liệu hệ thống",
                    false
            );

        return userAddressService.addUserAddress(userDetails.getUsername(), addUserAddressDTO);
    }
}
