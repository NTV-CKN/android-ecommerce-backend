package com.example.pkcn.dto.response.user_manage;

import com.example.pkcn.entity.UserAddress;

public class UserAddressDTO {
    private Integer id;
    private String phoneNumber;
    private String addressDetail;
    private String provinceCity;
    private Boolean isDefault;
    private String receiverName;

    public UserAddressDTO() {
    }

    public UserAddressDTO(Integer id, String phoneNumber,
                          String addressDetail, String provinceCity,
                          Boolean isDefault, String receiverName) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.addressDetail = addressDetail;
        this.provinceCity = provinceCity;
        this.isDefault = isDefault;
        this.receiverName = receiverName;
    }

    public void initUserAddress(UserAddress userAddress) {
        if (userAddress == null) {
            return;
        }
        this.id = userAddress.getId();
        this.phoneNumber = userAddress.getPhoneNumber();
        this.addressDetail = userAddress.getAddressDetail();
        this.provinceCity = userAddress.getProvinceCity();
        this.isDefault = userAddress.getDefault();
        this.receiverName = userAddress.getReceiverName();
    }

    public Integer getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public String getReceiverName() {
        return receiverName;
    }
}
