package com.example.pkcn.dto.response.user_manage.address;

import com.example.pkcn.entity.UserAddress;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserAddressDTO {
    private Integer id;
    @JsonProperty("phone_number")
    private String phoneNumber;
    @JsonProperty("address_detail")
    private String addressDetail;
    @JsonProperty("province_city")
    private String provinceCity;
    @JsonProperty("is_default")
    private Boolean isDefault;
    @JsonProperty("receiver_name")
    private String receiverName;
    private Double latitude;
    private Double longitude;


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
        this.latitude = userAddress.getLatitude();
        this.longitude = userAddress.getLongitude();
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

    public String getReceiverName() {
        return receiverName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
