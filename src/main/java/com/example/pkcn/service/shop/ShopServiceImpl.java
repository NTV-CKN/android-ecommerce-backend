package com.example.pkcn.service.shop;

import com.example.pkcn.controller.advice.cus_exception.DataNotFoundException;
import com.example.pkcn.dto.response.ShopInfoDTO;
import com.example.pkcn.entity.Shop;
import com.example.pkcn.repository.shop.IShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ShopServiceImpl implements IShopService {
    private final IShopRepository shopRepository;

    @Autowired
    public ShopServiceImpl(IShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public ShopInfoDTO getShopInfo() throws DataNotFoundException {
        Shop shopInfo = shopRepository.getShopInfo();
        if(shopInfo == null)
            throw new DataNotFoundException("Không tìm thấy thông tin shop");

        ShopInfoDTO shopInfoDTO = new ShopInfoDTO();
        shopInfoDTO.initData(shopInfo);

        return shopInfoDTO;
    }
}
