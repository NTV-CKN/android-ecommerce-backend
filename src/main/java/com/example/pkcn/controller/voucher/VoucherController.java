package com.example.pkcn.controller.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.response.VoucherDTO;
import com.example.pkcn.service.voucher.IVoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherController {

    private final IVoucherService voucherService;

    public VoucherController(IVoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherDTO>> getVoucher(@RequestParam(required = false) String typeCode,
                                       @RequestParam(required = false) DiscountType discountType,
                                       @RequestParam(required = false) String keyword) {
        List<VoucherDTO> res = voucherService.getVouchers(typeCode, discountType, keyword);
        return ResponseEntity.ok(res);
    }

}
