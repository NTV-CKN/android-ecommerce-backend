package com.example.pkcn.service.user.address;

import com.example.pkcn.dto.response.user_manage.address.UserAddressDTO;
import com.example.pkcn.entity.UserAddress;
import com.example.pkcn.repository.user.address.IUserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Primary
public class UserAddressServiceImpl implements IUserAddressService {
    private IUserAddressRepository userAddressRepository;

    @Autowired
    public UserAddressServiceImpl(IUserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    @Override
    public List<UserAddressDTO> getUserAddressListByUserId(Integer userId) {
        List<UserAddress> userAddresses
                = userAddressRepository.getUserAddressListByUserId(userId);

        if(userAddresses.isEmpty())
            return new ArrayList<>();

        List<UserAddressDTO> userAddressDTOS = new ArrayList<>();
        for(UserAddress userAddress: userAddresses) {
            UserAddressDTO userAddressDTO = new UserAddressDTO();
            userAddressDTO.initUserAddress(userAddress);

            userAddressDTOS.add(userAddressDTO);
        }

        return userAddressDTOS;
    }
}
