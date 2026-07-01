package com.example.pkcn.controller.admin.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.request.VoucherReqDTO;
import com.example.pkcn.dto.response.VoucherAdminDTO;
import com.example.pkcn.service.admin.voucher.IAdminVoucherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin-voucher")
public class AdminVoucherController {

    private final IAdminVoucherService adminVoucherService;

    public AdminVoucherController(IAdminVoucherService adminVoucherService) {
        this.adminVoucherService = adminVoucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherAdminDTO>> getVoucher(@RequestParam(required = false) String typeCode,
                                                            @RequestParam(required = false) DiscountType discountType,
                                                            @RequestParam(required = false) String keyword) {
        return ResponseEntity.ok(adminVoucherService.getAdminVoucher(typeCode, discountType, keyword));
    }

    @PostMapping
    public ResponseEntity<Void> createVoucher(@RequestBody VoucherReqDTO req) {
        adminVoucherService.createVoucher(req);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateVoucher(@PathVariable Integer id, @RequestBody VoucherReqDTO req) {
        adminVoucherService.updateVoucher(id, req);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoucher(@PathVariable Integer id) {
        adminVoucherService.deleteVoucher(id);
        return ResponseEntity.ok().build();
    }

}
