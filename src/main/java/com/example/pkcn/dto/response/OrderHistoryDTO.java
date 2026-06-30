package com.example.pkcn.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class OrderHistoryDTO {
    private Integer id;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryDate;
    private BigDecimal shippingFee;
    private BigDecimal totalMustPay;
    private String statusOrder;
    private String note;
    private String receiverName;
    private String addressDetail;
    private String provinceCity;

    public OrderHistoryDTO(Integer id, LocalDateTime orderDate, LocalDateTime deliveryDate, BigDecimal shippingFee, BigDecimal totalMustPay, String statusOrder, String note, String receiverName, String addressDetail, String provinceCity) {
        this.id = id;
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.shippingFee = shippingFee;
        this.totalMustPay = totalMustPay;
        this.statusOrder = statusOrder;
        this.note = note;
        this.receiverName = receiverName;
        this.addressDetail = addressDetail;
        this.provinceCity = provinceCity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getProvinceCity() {
        return provinceCity;
    }

    public void setProvinceCity(String provinceCity) {
        this.provinceCity = provinceCity;
    }
}
