package com.example.pkcn.service.admin.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.request.VoucherReqDTO;
import com.example.pkcn.dto.response.VoucherAdminDTO;
import com.example.pkcn.entity.Voucher;
import com.example.pkcn.entity.VoucherType;
import com.example.pkcn.repository.admin.voucher.IAdminVoucherRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdminVoucherServiceImpl implements IAdminVoucherService {

    IAdminVoucherRepository adminVoucherRepository;
    EntityManager em;

    public AdminVoucherServiceImpl(IAdminVoucherRepository adminVoucherRepository, EntityManager em) {
        this.adminVoucherRepository = adminVoucherRepository;
        this.em = em;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VoucherAdminDTO> getAdminVoucher(String typeCode, DiscountType discountType, String keyword) {
        return adminVoucherRepository.findAdminVoucher(typeCode, discountType, keyword);
    }

    @Override
    @Transactional
    public void createVoucher(VoucherReqDTO req) {
        Voucher voucher = new Voucher();

        voucher.setCode(req.getCode());
        voucher.setTitle(req.getTitle());
        voucher.setDiscountType(req.getDiscountType());
        voucher.setDiscountValue(req.getDiscountValue());
        voucher.setMinPriceAllow(req.getMinPriceAllow());
        voucher.setStartDate(req.getStartDate());
        voucher.setEndDate(req.getEndDate());
        voucher.setUsageLimit(req.getUsageLimit());

        // Các trường mặc định khi tạo mới
        voucher.setUsedCount(0);
        voucher.setStatus(1); // Mặc định kích hoạt

        VoucherType voucherType = em.find(VoucherType.class, req.getVoucherTypeId());
        if (voucherType == null) {
            throw new RuntimeException("Loại voucher không tồn tại!");
        }
        voucher.setVoucherType(voucherType);
        adminVoucherRepository.save(voucher);
    }

    @Override
    @Transactional
    public void updateVoucher(Integer id, VoucherReqDTO req) {
        Voucher v = adminVoucherRepository.findVoucherById(id);
        if (v == null) {
            throw new RuntimeException("Voucher không tồn tại!");
        }
        v.setCode(req.getCode());
        v.setTitle(req.getTitle());
        v.setDiscountType(req.getDiscountType());
        v.setDiscountValue(req.getDiscountValue());
        v.setMinPriceAllow(req.getMinPriceAllow());
        v.setStartDate(req.getStartDate());
        v.setEndDate(req.getEndDate());
        v.setUsageLimit(req.getUsageLimit());
        v.setStatus(req.getStatus());

        VoucherType voucherType = em.find(VoucherType.class, req.getVoucherTypeId());
        if (voucherType != null) {
            v.setVoucherType(voucherType);
        }
        adminVoucherRepository.update(v);
    }

    @Override
    @Transactional
    public void deleteVoucher(Integer id) {
        adminVoucherRepository.deleteById(id);
    }
}
