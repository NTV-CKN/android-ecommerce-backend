package com.example.pkcn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "ship_fee_by_address")
public class ShipFeeByAddress {
    @Id
    private Integer id;
    @Column(name = "province_city")
    private String provinceCity;
    private String type;
    private Double price;

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
}
