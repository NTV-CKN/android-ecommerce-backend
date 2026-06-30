package com.example.pkcn.repository.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.response.VoucherDTO;
import com.example.pkcn.entity.Voucher;

import java.util.List;

public interface IVoucherRepository {
    public List<VoucherDTO> findVouchers(String typeCode, DiscountType discountType, String keyword);
}
