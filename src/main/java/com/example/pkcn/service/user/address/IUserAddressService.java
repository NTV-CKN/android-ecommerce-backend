package com.example.pkcn.service.user.address;

import com.example.pkcn.dto.response.user_manage.address.UserAddressDTO;

import java.util.List;

public interface IUserAddressService {
    List<UserAddressDTO> getUserAddressListByUserId(Integer userId);
}
