package com.example.pkcn.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateUserAddressDTO {
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


    public UpdateUserAddressDTO() {
    }

    public UpdateUserAddressDTO(Integer id, String phoneNumber,
                                String addressDetail, String provinceCity,
                                Boolean isDefault, String receiverName) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.addressDetail = addressDetail;
        this.provinceCity = provinceCity;
        this.isDefault = isDefault;
        this.receiverName = receiverName;
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

    public Boolean getDefault() {
        return isDefault;
    }
}
