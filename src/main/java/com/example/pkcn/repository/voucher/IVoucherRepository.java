package com.example.pkcn.repository.voucher;

import com.example.pkcn.dto.response.VoucherDTO;
import com.example.pkcn.entity.Voucher;

import java.util.List;

public interface IVoucherRepository {
    List<VoucherDTO> findVoucherByType(Integer typeId, String keyword);
}
