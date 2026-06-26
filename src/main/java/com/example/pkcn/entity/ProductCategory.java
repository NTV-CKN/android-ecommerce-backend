package com.example.pkcn.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_categories")
public class ProductCategory {

    @EmbeddedId
    private ProductCategoryId id = new ProductCategoryId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId") // Khớp với tên biến 'productId' trong ProductCategoryId bên dưới
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("categoryId") // Khớp với tên biến 'categoryId' trong ProductCategoryId bên dưới
    @JoinColumn(name = "category_id")
    private Categories category;

    // Construtors
    public ProductCategory() {}

    public ProductCategory(Product product, Categories category) {
        this.product = product;
        this.category = category;
        // Tự động gán ID phức hợp khi khởi tạo bằng object
        this.id = new ProductCategoryId(product.getId(), category.getId());
    }

    // Getters and Setters
    public ProductCategoryId getId() { return id; }
    public void setId(ProductCategoryId id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Categories getCategory() { return category; }
    public void setCategory(Categories category) { this.category = category; }

    // --- CLASS KHÓA PHỨC HỢP (Bắt buộc phải thêm @Embeddable) ---
    @Embeddable
    public static class ProductCategoryId implements Serializable {

        @Column(name = "product_id")
        private Integer productId;

        @Column(name = "category_id")
        private Integer categoryId;

        public ProductCategoryId() {}

        public ProductCategoryId(Integer productId, Integer categoryId) {
            this.productId = productId;
            this.categoryId = categoryId;
        }

        public Integer getProductId() { return productId; }
        public void setProductId(Integer productId) { this.productId = productId; }

        public Integer getCategoryId() { return categoryId; }
        public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

        // BẮT BUỘC phải override equals và hashCode cho Composite Key
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductCategoryId Republic = (ProductCategoryId) o;
            return Objects.equals(productId, Republic.productId) && Objects.equals(categoryId, Republic.categoryId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, categoryId);
        }
    }
}