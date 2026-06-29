package com.example.pkcn.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vouchers")
public class Voucher {

    public enum DiscountType {
        FIXED_AMOUNT, PERCENTAGE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, name = "code")
    private String code;
    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type")
    private DiscountType discountType;

    @Column(name = "discount_value")
    private BigDecimal discountValue;

    @Column(name = "min_price_allow")
    private BigDecimal minPriceAllow;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "usage_limit")
    private Integer usageLimit;

    @Column(name = "used_count")
    private Integer usedCount;

    private Integer status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voucher_type_id")
    private VoucherType voucherType;

    public Voucher(String code, String title, DiscountType discountType, BigDecimal discountValue,
                   BigDecimal minPriceAllow, LocalDateTime startDate, LocalDateTime endDate,
                   Integer usageLimit, VoucherType voucherType) {
        this.code = code;
        this.title = title;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minPriceAllow = minPriceAllow;
        this.startDate = startDate;
        this.endDate = endDate;
        this.usageLimit = usageLimit;
        this.usedCount = 0;
        this.status = 1;
        this.voucherType = voucherType;
    }

    public Voucher() {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public Integer getUsedCount() {
        return usedCount;
    }

    public void setUsedCount(Integer usedCount) {
        this.usedCount = usedCount;
    }

    public Integer getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public VoucherType getVoucherType() {
        return voucherType;
    }
}