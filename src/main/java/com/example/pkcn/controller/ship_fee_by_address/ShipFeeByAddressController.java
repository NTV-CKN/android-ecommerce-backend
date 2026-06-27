package com.example.pkcn.controller.ship_fee_by_address;


import com.example.pkcn.common.JwtUtils;
import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.response.user_manage.address.ShipFeeByAddressDTO;
import com.example.pkcn.entity.ShipFeeByAddress;
import com.example.pkcn.entity.User;
import com.example.pkcn.repository.user.user_detail_repo.IUserRepository;
import com.example.pkcn.service.ship_fee_by_address.IShipFeeByAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/ship-fee-address")
@RestController
public class ShipFeeByAddressController {
    private final IShipFeeByAddressService shipFeeByAddressService;

    @Autowired
    public ShipFeeByAddressController(
            IShipFeeByAddressService shipFeeByAddressService
    ) {
        this.shipFeeByAddressService = shipFeeByAddressService;
    }


    @GetMapping("/view-ship-fee-by-address")
    public List<ShipFeeByAddressDTO> getShipFeeByAddresses() {
        return shipFeeByAddressService.getShipFeeByAddresses();
    }

    @GetMapping("/get-ship-fee-by-province-city")
    public ShipFeeByAddressDTO getShipFeeByProvinceCity(
            @RequestParam String provinceCity
    ) throws UserNotExistException {
        return shipFeeByAddressService.getShipFeeByProvinceCity(provinceCity);
    }
}
