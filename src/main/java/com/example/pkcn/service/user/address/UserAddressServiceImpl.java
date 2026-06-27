package com.example.pkcn.service.user.address;

import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.request.AddUserAddressDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;
import com.example.pkcn.dto.response.user_manage.address.UserAddressDTO;
import com.example.pkcn.entity.User;
import com.example.pkcn.entity.UserAddress;
import com.example.pkcn.repository.user.address.IUserAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public SuccessBasicDTO addUserAddress(String email, AddUserAddressDTO addUserAddressDTO) throws Exception {
        User user = userAddressRepository.getUserByEmailAndFetchAddresses(email);
        if(user == null)
            throw new UserNotExistException("Người dùng không tồn tại");

        UserAddress userAddress = new UserAddress(
                addUserAddressDTO.getPhoneNumber(),
                addUserAddressDTO.getAddressDetail(),
                addUserAddressDTO.getProvinceCity(),
                addUserAddressDTO.getSelected(),
                addUserAddressDTO.getReceiverName(),
                addUserAddressDTO.getLatitude(),
                addUserAddressDTO.getLongitude()
        );

        if(userAddress.getDefault() || !user.hasAddressDefault()) {
            user.getUserAddresses()
                    .forEach(userAddressTmp -> userAddressTmp.setDefault(false));

            userAddress.setDefault(true);
        }

        userAddress.setUser(user);
        user.addAddress(userAddress);

        return new SuccessBasicDTO(
                "Thêm địa chỉ người dùng thành công",
                true
        );
    }
}
