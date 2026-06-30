package com.example.pkcn.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "address_order")
public class AddressOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "receiver_name")
    private String receiverName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address_detail")
    private String addressDetail;
    @Column(name = "district")
    private String district;
    @Column(name = "province_city")
    private String provinceCity;
    @Column(name = "note")
    private String note;

    public AddressOrder(Integer id, String receiverName, String phoneNumber, String addressDetail, String district, String provinceCity, String note) {
        this.id = id;
        this.receiverName = receiverName;
        this.phoneNumber = phoneNumber;
        this.addressDetail = addressDetail;
        this.district = district;
        this.provinceCity = provinceCity;
        this.note = note;
    }
    public AddressOrder() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
