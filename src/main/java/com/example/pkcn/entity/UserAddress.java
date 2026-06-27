package com.example.pkcn.entity;

import com.example.pkcn.dto.request.UpdateUserAddressDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "user_address")
public class UserAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address_detail")
    private String addressDetail;
    @Column(name = "province_city")
    private String provinceCity;
    @Column(name = "is_selected")
    private Boolean isDefault;
    @Column(name = "receiver_name")
    private String receiverName;
    private Double latitude;
    private Double longitude;

    public UserAddress(String phoneNumber, String addressDetail,
                       String provinceCity, Boolean isDefault,
                       String receiverName, Double lat, Double lng) {
        this.phoneNumber = phoneNumber;
        this.addressDetail = addressDetail;
        this.provinceCity = provinceCity;
        this.isDefault = isDefault;
        this.receiverName = receiverName;
        this.latitude = lat;
        this.longitude = lng;
    }

    public UserAddress() {
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
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

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void updateFrom(UpdateUserAddressDTO other) {
        if (other == null) return;

        this.phoneNumber = other.getPhoneNumber();
        this.addressDetail = other.getAddressDetail();
        this.provinceCity = other.getProvinceCity();
        this.isDefault = other.getDefault();
        this.receiverName = other.getReceiverName();
        this.latitude = other.getLatitude();
        this.longitude = other.getLongitude();
    }
}
