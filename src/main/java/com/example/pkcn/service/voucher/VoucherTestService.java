package com.example.pkcn.service.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.response.VoucherDTO;
import com.example.pkcn.utils.VoucherJsonUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VoucherTestService {

    public String createVoucherJson() {

        VoucherDTO voucher1 =
                new VoucherDTO(
                        1,
                        "SHIPFREE",
                        "Free Ship",
                        DiscountType.FIXED_AMOUNT,
                        new BigDecimal("30000"),
                        new BigDecimal("0"),
                        LocalDateTime.now(),
                        1,
                        "SHIP",
                        "Shipping Voucher"
                );

        VoucherDTO voucher2 =
                new VoucherDTO(
                        2,
                        "SALE50K",
                        "Giảm 50K",
                        DiscountType.FIXED_AMOUNT,
                        new BigDecimal("50000"),
                        new BigDecimal("300000"),
                        LocalDateTime.now(),
                        2,
                        "ORDER",
                        "Order Voucher"
                );

        List<VoucherDTO> vouchers =
                List.of(voucher1, voucher2);

        return VoucherJsonUtil.toJson(vouchers);
    }
}