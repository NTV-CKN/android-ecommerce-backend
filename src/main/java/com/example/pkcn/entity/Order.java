package com.example.pkcn.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address_order_id")
    private Integer addressOrderId;

    @Column(name = "payment_method_id")
    private Integer paymentMethodId;

    @Column(name = "shipping_fee")
    private BigDecimal shippingFee;

    @Column(name = "total_must_pay")
    private BigDecimal totalMustPay;

    @Column(name = "status_order")
    private String statusOrder;

    private String note;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;
    @PrePersist
    protected void onCreate() {
        this.orderDate = LocalDateTime.now();
        if (this.statusOrder == null) {
            this.statusOrder = "pending";
        }
        if (this.shippingFee == null) {
            this.shippingFee = BigDecimal.ZERO;
        }
    }

    public Order() {
    }
    public Order(User user, Integer addressOrderId, Integer paymentMethodId,
                 BigDecimal shippingFee, BigDecimal totalMustPay, String statusOrder,
                 String note, LocalDateTime deliveryDate) {
        this.user = user;
        this.addressOrderId = addressOrderId;
        this.paymentMethodId = paymentMethodId;
        this.shippingFee = shippingFee;
        this.totalMustPay = totalMustPay;
        this.statusOrder = statusOrder;
        this.note = note;
        this.deliveryDate = deliveryDate;
    }

    public Integer getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    public BigDecimal getTotalMustPay() {
        return totalMustPay;
    }

    public void setTotalMustPay(BigDecimal totalMustPay) {
        this.totalMustPay = totalMustPay;
    }

    public String getStatusOrder() {
        return statusOrder;
    }

    public void setStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }
}