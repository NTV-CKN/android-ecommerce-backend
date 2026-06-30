package com.example.pkcn.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderManageDTO {

    private Integer orderId;

    private String customerName;

    private BigDecimal totalPrice;

    private String status;

    private LocalDateTime orderDate;


    public OrderManageDTO(
            Integer orderId,
            String customerName,
            BigDecimal totalPrice,
            String status,
            LocalDateTime orderDate
    ) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.totalPrice = totalPrice;
        this.status = status;
        this.orderDate = orderDate;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }
}