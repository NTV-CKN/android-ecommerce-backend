package com.example.pkcn.service.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.response.VoucherDTO;
import com.example.pkcn.repository.voucher.IVoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherServiceImpl implements IVoucherService{
    private final IVoucherRepository repository;
    public VoucherServiceImpl(IVoucherRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VoucherDTO> getVouchers(String typeCode, DiscountType discountType, String keyword) {
        return repository.findVouchers(typeCode, discountType, keyword);
    }
}
