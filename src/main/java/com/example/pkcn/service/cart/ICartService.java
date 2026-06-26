package com.example.pkcn.service.cart;

import com.example.pkcn.dto.request.CartLocalDTO;
import com.example.pkcn.dto.response.CartDTO;
import com.example.pkcn.entity.Cart;

import java.util.List;

public interface ICartService {
    CartDTO getCart(Integer userId);
    Cart getOrCreateCart(Integer userId);
    CartDTO updateQuantity(Integer userId, Integer itemId, Integer qty);
    CartDTO removeItem(Integer userId, Integer itemId);
    CartDTO mergeLocalCart(Integer userId, List<CartLocalDTO> localList);
    CartDTO deleteAll(Integer userId);
    Long countTotalVariant(Integer userId);
}
