package com.example.pkcn.service.shop;

import com.example.pkcn.controller.advice.cus_exception.DataNotFoundException;
import com.example.pkcn.dto.response.ShopInfoDTO;

public interface IShopService {
    ShopInfoDTO getShopInfo() throws DataNotFoundException;
}
