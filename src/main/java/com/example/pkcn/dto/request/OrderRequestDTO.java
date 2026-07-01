package com.example.pkcn.dto.request;

import java.util.List;

public class OrderRequestDTO {
    private Integer addressOrderId;
    private Integer paymentMethodId;
    private String note;
    private List<String> appliedVouchers;
    private List<ProductItem> products;
    public static class ProductItem {
        private Integer productVariantId;
        private Integer quantity;

        public Integer getProductVariantId() {
            return productVariantId;
        }

        public void setProductVariantId(Integer productVariantId) {
            this.productVariantId = productVariantId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }

    public Integer getAddressOrderId() {
        return addressOrderId;
    }

    public void setAddressOrderId(Integer addressOrderId) {
        this.addressOrderId = addressOrderId;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<String> getAppliedVouchers() {
        return appliedVouchers;
    }

    public void setAppliedVouchers(List<String> appliedVouchers) {
        this.appliedVouchers = appliedVouchers;
    }

    public List<ProductItem> getProducts() {
        return products;
    }

    public void setProducts(List<ProductItem> products) {
        this.products = products;
    }
}
