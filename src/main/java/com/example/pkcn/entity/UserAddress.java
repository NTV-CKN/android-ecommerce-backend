package com.example.pkcn.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_address")
public class UserAddress {
    @Id
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
}
