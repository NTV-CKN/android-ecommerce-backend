package com.example.pkcn.repository.cart_item;

import com.example.pkcn.entity.CartItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ICartItemRepository {
    //load ds item vao cart
    public List<CartItem> findByCardId(Integer cartId);
    //kiem tra xem san pham do da co san o trong cart chua
    public Optional<CartItem> findByCartIdAndProductVariantId(Integer cartId, Integer productVariantId);

    @Transactional
    CartItem save(CartItem item);

    @Transactional
    void delete(CartItem item);

    void deleteAllById(Integer cartId);

}
