package com.example.pkcn.repository.admin.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.response.VoucherAdminDTO;
import com.example.pkcn.entity.Voucher;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface IAdminVoucherRepository {
    Voucher findVoucherById(Integer id);
    List<VoucherAdminDTO> findAdminVoucher(String typeCode, DiscountType discountType, String keyword);
    void save(Voucher voucher);
    void update(Voucher voucher);
    void deleteById(Integer id);
}
