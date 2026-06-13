package com.example.pkcn.controller.ship_fee_by_address;


import com.example.pkcn.dto.response.user_manage.address.ShipFeeByAddressDTO;
import com.example.pkcn.service.ship_fee_by_address.IShipFeeByAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
