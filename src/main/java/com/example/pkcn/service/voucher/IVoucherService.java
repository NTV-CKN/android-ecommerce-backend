package com.example.pkcn.service.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.response.VoucherDTO;

import java.util.List;

public interface IVoucherService {
    List<VoucherDTO> getVouchers(String typeCode, DiscountType discountType, String keyword);
}
