package com.example.pkcn.service.user.address;

import com.example.pkcn.controller.advice.cus_exception.DataNotFoundException;
import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.request.AddUserAddressDTO;
import com.example.pkcn.dto.request.UpdateUserAddressDTO;
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
import java.util.Objects;

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
    //Logic nếu địa chỉ thêm lên có default = true thì set false cho các địa chỉ khác
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

    @Override
    @Transactional
    //Logic nếu địa chỉ thêm lên không set default = true nhưng trong địa chỉ user hiện có default = false thì hủy
    public SuccessBasicDTO updateUserAddress(
            String email,
            UpdateUserAddressDTO updateUserAddressDTO
    ) throws Exception {
        User user = userAddressRepository.getUserByEmailAndFetchAddresses(email);
        if(user == null)
            throw new UserNotExistException("Người dùng không tồn tại");

        for(UserAddress userAddress: user.getUserAddresses()) {
            if(Objects.equals(userAddress.getId(), updateUserAddressDTO.getId())) {
                if(!updateUserAddressDTO.getDefault() && userAddress.getDefault() && user.hasAddressDefault())
                    return new SuccessBasicDTO(
                            "Vui lòng cập nhật địa chỉ mặc định cho địa chỉ khác trước khi cập nhật địa chỉ này",
                            false
                    );

                userAddress.updateFrom(updateUserAddressDTO);
            }else {
                if(updateUserAddressDTO.getDefault())
                    userAddress.setDefault(false);
            }
        }

        return new SuccessBasicDTO(
                "Cập nhật thành công",
                true
        );
    }

    @Transactional
    @Override
    public SuccessBasicDTO removeUserAddress(UserAddressDTO userAddressDTO, String email) throws UserNotExistException {
        User user = userAddressRepository.getUserByEmailAndFetchAddresses(email);
        if(user == null)
            throw new UserNotExistException("Người dùng không tồn tại");

        UserAddress userAddressRemove = null;
        for(UserAddress userAddress: user.getUserAddresses()) {
            if(userAddress.getDefault() && Objects.equals(userAddress.getId(), userAddressDTO.getId())) {
                throw new IllegalArgumentException(
                        "Không thể xóa địa chỉ mặc định"
                );
            }

            if(Objects.equals(userAddress.getId(), userAddressDTO.getId())) {
                userAddressRemove=userAddress;
                break;
            }
        }

        if(userAddressRemove == null)
            throw new DataNotFoundException(
                    "Không tìm thấy địa chỉ phù hợp để xóa"
            );

        userAddressRemove.setUser(null);
        user.getUserAddresses().remove(userAddressRemove);

        return new SuccessBasicDTO(
                "Xóa địa chỉ thành công",
                true
        );
    }
}
