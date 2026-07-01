package com.example.pkcn.dto.request;

import com.example.pkcn.common.DiscountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VoucherReqDTO {

    private String code;
    private String title;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal minPriceAllow;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer usageLimit;
    private Integer status;
    private Integer voucherTypeId;

    public VoucherReqDTO(String code, String title, DiscountType discountType, BigDecimal discountValue, BigDecimal minPriceAllow, LocalDateTime startDate, LocalDateTime endDate, Integer usageLimit, Integer status, Integer voucherTypeId) {
        this.code = code;
        this.title = title;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minPriceAllow = minPriceAllow;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usageLimit = usageLimit;
        this.status = status;
        this.voucherTypeId = voucherTypeId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getMinPriceAllow() {
        return minPriceAllow;
    }

    public void setMinPriceAllow(BigDecimal minPriceAllow) {
        this.minPriceAllow = minPriceAllow;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getVoucherTypeId() {
        return voucherTypeId;
    }

    public void setVoucherTypeId(Integer voucherTypeId) {
        this.voucherTypeId = voucherTypeId;
    }
}
