package com.example.pkcn.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDetailAdminDTO {

    private Integer orderId;

    private String customerName;

    private String customerEmail;

    private BigDecimal totalPrice;

    private BigDecimal shippingFee;

    private String status;

    private String note;

    private Integer paymentMethodId;

    private LocalDateTime orderDate;

    private List<OrderItemDTO> items;

    private List<VoucherDTO> appliedVouchers;

    public OrderDetailAdminDTO(
            Integer orderId,
            String customerName,
            String customerEmail,
            BigDecimal totalPrice,
            BigDecimal shippingFee,
            String status,
            String note,
            Integer paymentMethodId,
            LocalDateTime orderDate,
            List<OrderItemDTO> items,
            List<VoucherDTO> appliedVouchers
    ) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.totalPrice = totalPrice;
        this.shippingFee = shippingFee;
        this.status = status;
        this.note = note;
        this.paymentMethodId = paymentMethodId;
        this.orderDate = orderDate;
        this.items = items;
        this.appliedVouchers = appliedVouchers;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }
    public List<VoucherDTO> getAppliedVouchers() {
        return appliedVouchers;
    }
}