package com.example.pkcn.controller.auth;

import com.example.pkcn.controller.advice.cus_exception.DataNotFoundException;
import com.example.pkcn.dto.response.ShopInfoDTO;
import com.example.pkcn.service.shop.IShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/shop")
@RestController
public class ShopController {
    private IShopService shopService;

    @Autowired
    public ShopController(IShopService shopService) {
        this.shopService = shopService;
    }

    @GetMapping("/info")
    public ShopInfoDTO getShopInfo() throws DataNotFoundException {
        return shopService.getShopInfo();
    }
}
