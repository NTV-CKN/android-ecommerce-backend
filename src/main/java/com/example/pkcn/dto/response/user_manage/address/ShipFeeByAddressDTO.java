package com.example.pkcn.dto.response.user_manage.address;

import com.example.pkcn.entity.ShipFeeByAddress;

public class ShipFeeByAddressDTO {
    private Integer id;
    private String provinceCity;
    private String type;
    private Double price;
    private Double latitude;
    private Double longitude;

    public void initData(ShipFeeByAddress shipFeeByAddress) {
        if (shipFeeByAddress != null) {
            this.id = shipFeeByAddress.getId();
            this.provinceCity = shipFeeByAddress.getProvinceCity();
            this.type = shipFeeByAddress.getType();
            this.price = shipFeeByAddress.getPrice();
            this.longitude = shipFeeByAddress.getLongitude();
            this.latitude = shipFeeByAddress.getLatitude();
        }
    }

    public Integer getId() {
        return id;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public String getType() {
        return type;
    }

    public Double getPrice() {
        return price;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
