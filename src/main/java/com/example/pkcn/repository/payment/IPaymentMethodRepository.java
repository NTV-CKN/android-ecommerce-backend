package com.example.pkcn.repository.payment;

import com.example.pkcn.dto.response.PaymentMethodDTO;
import com.example.pkcn.entity.PaymentMethod;

import java.util.List;

public interface IPaymentMethodRepository {
    List<PaymentMethod> getPaymentMethods();
}
