package com.example.pkcn.service.admin.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.request.VoucherReqDTO;
import com.example.pkcn.dto.response.VoucherAdminDTO;
import com.example.pkcn.entity.Voucher;

import java.util.List;

public interface IAdminVoucherService {
    List<VoucherAdminDTO> getAdminVoucher(String typeCode, DiscountType discountType, String keyword);
    void createVoucher(VoucherReqDTO req);
    void updateVoucher(Integer id, VoucherReqDTO req);
    void deleteVoucher(Integer id);
}
