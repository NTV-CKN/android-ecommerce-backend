package com.example.pkcn.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VoucherDTO {
    private Long id;
    private String code;
    private String title;
    private String discountType;
    private BigDecimal discountValue;
    private BigDecimal minPriceAllow;
    private LocalDateTime endDate;

    public VoucherDTO(Long id, String code, String title, String discountType,
                      BigDecimal discountValue, BigDecimal minPriceAllow, LocalDateTime endDate) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minPriceAllow = minPriceAllow;
        this.endDate = endDate;
    }

    public Long getId() { return id; }
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public String getDiscountType() { return discountType; }
    public BigDecimal getDiscountValue() { return discountValue; }
    public BigDecimal getMinPriceAllow() { return minPriceAllow; }
    public LocalDateTime getEndDate() { return endDate; }
}