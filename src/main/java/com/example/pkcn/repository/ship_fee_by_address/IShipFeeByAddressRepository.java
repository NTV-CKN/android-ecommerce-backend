package com.example.pkcn.repository.ship_fee_by_address;

import com.example.pkcn.entity.ShipFeeByAddress;

import java.util.List;

public interface IShipFeeByAddressRepository {
    List<ShipFeeByAddress> getShipFeeByAddresses();
    Boolean checkMatchProvinceCity(String provinceCity);
    ShipFeeByAddress getShipFeeByProvinceCity(String provinceCity);
}
