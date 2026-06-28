package com.example.pkcn.controller.payment;

import com.example.pkcn.dto.response.PaymentMethodDTO;
import com.example.pkcn.service.payment.IPaymentMethodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payment-methods")
public class PaymentMethodController {

    final IPaymentMethodService paymentMethodService;

    public PaymentMethodController(IPaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentMethodDTO>> getPaymentMethods() {
        List<PaymentMethodDTO> list = paymentMethodService.getPaymentMethods();
        return ResponseEntity.ok(list);
    }
}