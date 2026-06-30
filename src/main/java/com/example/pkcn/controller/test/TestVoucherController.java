package com.example.pkcn.controller.test;

import com.example.pkcn.service.voucher.VoucherTestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestVoucherController {

    private final VoucherTestService service;

    public TestVoucherController(
            VoucherTestService service
    ) {
        this.service = service;
    }

    @GetMapping("/test-voucher-json")
    public String test() {

        return service.createVoucherJson();
    }
}