package com.example.pkcn.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "contact_shops")
public class Shop {
    @Id
    private Integer id;
    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "shop_address")
    private String shopAddress;
    @Column(name = "shop_phone_number")
    private String phoneNumber;
    @Column(name = "shop_hotline")
    private String hotline;
    @Column(name = "shop_email")
    private String email;
    @Column(name = "img_url")
    private String image;
    @Column(name = "status")
    private Boolean status;

    public Integer getId() {
        return id;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getHotline() {
        return hotline;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public Boolean getStatus() {
        return status;
    }
}
