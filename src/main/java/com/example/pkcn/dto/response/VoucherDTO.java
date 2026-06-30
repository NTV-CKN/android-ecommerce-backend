package com.example.pkcn.dto.response;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.entity.Voucher;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VoucherDTO {
    private Integer id;
    private String code;
    private String title;
    private DiscountType discountType;
    private BigDecimal discountValue;
    private BigDecimal minPriceAllow;
    private LocalDateTime endDate;
    private VoucherTypeDTO voucherType;

    public VoucherDTO(Integer id, String code, String title, DiscountType discountType,
                      BigDecimal discountValue, BigDecimal minPriceAllow, LocalDateTime endDate,
                      Integer voucherTypeId, String voucherTypeCode, String voucherTypeName) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minPriceAllow = minPriceAllow;
        this.endDate = endDate;
        this.voucherType = new VoucherTypeDTO(voucherTypeId, voucherTypeCode, voucherTypeName);
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public BigDecimal getMinPriceAllow() {
        return minPriceAllow;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
    public VoucherTypeDTO getVoucherType() { return voucherType; }
}