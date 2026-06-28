package com.example.pkcn.service.payment;

import com.example.pkcn.dto.response.PaymentMethodDTO;

import java.util.List;

public interface IPaymentMethodService {
    List<PaymentMethodDTO> getPaymentMethods();
}
