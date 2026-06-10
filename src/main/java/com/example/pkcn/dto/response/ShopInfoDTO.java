package com.example.pkcn.dto.response;

import com.example.pkcn.entity.Shop;

public class ShopInfoDTO {
    private Integer id;
    private String shopName;
    private String shopAddress;
    private String phoneNumber;
    private String hotline;
    private String email;
    private String image;
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

    public void initData(Shop shop) {
        this.id = shop.getId();
        this.shopName = shop.getShopName();
        this.shopAddress = shop.getShopAddress();
        this.phoneNumber = shop.getPhoneNumber();
        this.hotline = shop.getHotline();
        this.email = shop.getEmail();
        this.image = shop.getImage();
        this.status = shop.getStatus();
    }
}
