package com.example.pkcn.repository.user.address;

import com.example.pkcn.entity.User;
import com.example.pkcn.entity.UserAddress;

import java.util.List;

public interface IUserAddressRepository {
    List<UserAddress> getUserAddressListByUserId(Integer userId);
    User getUserByEmailAndFetchAddresses(String email);
}
