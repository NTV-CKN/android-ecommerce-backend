package com.example.pkcn.entity;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;
import jakarta.persistence.Column;

@Embeddable
public class ProductCategoryId implements Serializable {

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "category_id")
    private Integer categoryId;

    public ProductCategoryId(){}

    public ProductCategoryId(Integer productId, Integer categoryId){
        this.productId = productId;
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof ProductCategoryId)) return false;

        ProductCategoryId that = (ProductCategoryId) o;

        return Objects.equals(productId, that.productId)
                && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode(){
        return Objects.hash(productId, categoryId);
    }
}