package com.example.pkcn.service.ship_fee_by_address;

import com.example.pkcn.dto.response.user_manage.address.ShipFeeByAddressDTO;

import java.util.List;

public interface IShipFeeByAddressService {
    List<ShipFeeByAddressDTO> getShipFeeByAddresses();
    Boolean checkMatchProvinceCity(String provinceCity);
}
