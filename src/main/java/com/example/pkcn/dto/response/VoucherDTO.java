package com.example.pkcn.dto.response;

import com.example.pkcn.entity.Voucher;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VoucherDTO {
    private Integer id;
    private String code;
    private String title;
    private Voucher.DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal minPriceAllow;
    private LocalDateTime endDate;

    public VoucherDTO(Integer id, String code, String title, Voucher.DiscountType discountType,
                      BigDecimal discountValue, BigDecimal minPriceAllow, LocalDateTime endDate) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minPriceAllow = minPriceAllow;
        this.endDate = endDate;
    }

    public Integer getId() { return id; }
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public Voucher.DiscountType getDiscountType() { return discountType; }
    public BigDecimal getDiscountValue() { return discountValue; }
    public BigDecimal getMinPriceAllow() { return minPriceAllow; }
    public LocalDateTime getEndDate() { return endDate; }
}