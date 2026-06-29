package com.example.pkcn.service.voucher;

import com.example.pkcn.dto.response.VoucherDTO;
import com.example.pkcn.repository.voucher.IVoucherRepository;

import java.util.List;

public class VoucherServiceImpl implements IVoucherService{
    private final IVoucherRepository repository;
    public VoucherServiceImpl(IVoucherRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VoucherDTO> getVouchers() {
        return List.of();
    }
}
