package com.example.pkcn.dto.response;

import java.math.BigDecimal;

public class OrderItemDTO {

    private String productName;

    private Integer quantity;

    private BigDecimal price;

    public OrderItemDTO(
            String productName,
            Integer quantity,
            BigDecimal price
    ) {
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}