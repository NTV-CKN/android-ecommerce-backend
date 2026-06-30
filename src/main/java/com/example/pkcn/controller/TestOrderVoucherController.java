package com.example.pkcn.controller;

import com.example.pkcn.service.order.OrderVoucherTestService;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestOrderVoucherController {

    private final OrderVoucherTestService service;

    public TestOrderVoucherController(
            OrderVoucherTestService service
    ) {
        this.service = service;
    }

    @GetMapping("/test-order-voucher")
    public String test(
            @RequestParam Integer orderId
    ) {

        service.insertFakeVoucherToOrder(
                orderId
        );

        return "DONE";
    }
}