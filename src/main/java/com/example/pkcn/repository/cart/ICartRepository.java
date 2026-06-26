package com.example.pkcn.repository.cart;

import com.example.pkcn.entity.Cart;

import java.util.Optional;

public interface ICartRepository {
    public Optional<Cart> findCartByUserIdAndIsValid(Integer userId);
    public Cart save(Cart cart);
}
