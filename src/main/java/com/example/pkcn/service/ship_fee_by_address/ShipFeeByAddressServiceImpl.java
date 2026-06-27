package com.example.pkcn.service.ship_fee_by_address;

import com.example.pkcn.dto.response.user_manage.address.ShipFeeByAddressDTO;
import com.example.pkcn.entity.ShipFeeByAddress;
import com.example.pkcn.repository.ship_fee_by_address.IShipFeeByAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class ShipFeeByAddressServiceImpl implements IShipFeeByAddressService{
    private IShipFeeByAddressRepository shipFeeByAddressRepository;

    @Autowired
    public ShipFeeByAddressServiceImpl(IShipFeeByAddressRepository shipFeeByAddressRepository) {
        this.shipFeeByAddressRepository = shipFeeByAddressRepository;
    }

    @Override
    public List<ShipFeeByAddressDTO> getShipFeeByAddresses() {
        List<ShipFeeByAddressDTO> results = new ArrayList<>();
        for(ShipFeeByAddress shipFeeByAddress: shipFeeByAddressRepository.getShipFeeByAddresses()) {
            ShipFeeByAddressDTO shipFeeByAddressDTO = new ShipFeeByAddressDTO();
            shipFeeByAddressDTO.initData(shipFeeByAddress);

            results.add(shipFeeByAddressDTO);
        }

        return results;
    }

    @Override
    public Boolean checkMatchProvinceCity(String provinceCity) {
        return shipFeeByAddressRepository.checkMatchProvinceCity(provinceCity);
    }
}
