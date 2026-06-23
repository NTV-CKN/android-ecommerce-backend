package com.example.pkcn.dto.response;

import com.example.pkcn.entity.Product;

import java.math.BigDecimal;

public class ProductDetailsDTO {
    private Integer id;
    private String name;
    private String subtitle;
    private String description;
    private Integer warrantyPeriod;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private String mainImage;

    public ProductDetailsDTO(Product product) {
    }

    public ProductDetailsDTO(Integer id, String name, String subtitle, String description, Integer warrantyPeriod, BigDecimal minPrice, BigDecimal maxPrice, String mainImage) {
        this.id = id;
        this.name = name;
        this.subtitle = subtitle;
        this.description = description;
        this.warrantyPeriod = warrantyPeriod;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.mainImage = mainImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(Integer warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }
}
