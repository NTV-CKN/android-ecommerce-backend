package com.example.pkcn.service.user.address;

import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.request.AddUserAddressDTO;
import com.example.pkcn.dto.request.UpdateUserAddressDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;
import com.example.pkcn.dto.response.user_manage.address.UserAddressDTO;
import com.example.pkcn.entity.UserAddress;

import java.util.List;

public interface IUserAddressService {
    List<UserAddressDTO> getUserAddressListByUserId(Integer userId);
    SuccessBasicDTO addUserAddress(String email, AddUserAddressDTO addUserAddressDTO) throws Exception;
    SuccessBasicDTO updateUserAddress(String email, UpdateUserAddressDTO updateUserAddressDTO) throws Exception;
    SuccessBasicDTO removeUserAddress(UserAddressDTO userAddressDTO, String email) throws UserNotExistException;
}
