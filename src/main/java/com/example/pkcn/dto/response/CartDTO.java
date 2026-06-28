package com.example.pkcn.dto.response;

import java.math.BigDecimal;
import java.util.List;

public class CartDTO {
    private Integer id;
    private BigDecimal totalPrice;
    private List<CartItemDTO> cartItems;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItemDTO> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDTO> cartItems) {
        this.cartItems = cartItems;
    }
}
