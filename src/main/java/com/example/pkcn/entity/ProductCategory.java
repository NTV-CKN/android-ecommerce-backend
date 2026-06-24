package com.example.pkcn.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_categories")
@IdClass(ProductCategory.ProductCategoryId.class)
public class ProductCategory {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Categories category;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Categories getCategory() {
        return category;
    }

    public void setCategory(Categories category) {
        this.category = category;
    }

    public static class ProductCategoryId implements Serializable {
        private Integer product;
        private Integer category;

        public ProductCategoryId() {}

        public ProductCategoryId(Integer product, Integer category) {
            this.product = product;
            this.category = category;
        }

        public Integer getProduct() {
            return product;
        }

        public void setProduct(Integer product) {
            this.product = product;
        }

        public Integer getCategory() {
            return category;
        }

        public void setCategory(Integer category) {
            this.category = category;
        }
    }
}