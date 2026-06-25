package com.example.pkcn.repository.cart;

import com.example.pkcn.entity.Cart;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class CartRepositoryImpl implements ICartRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public Optional<Cart> findCartByUserIdAndIsValid(Integer userId) {
        String query = "SELECT c FROM Cart c WHERE c.userId = :userId AND c.isValid = true";
        return em.createQuery(query, Cart.class)
                .setParameter("userId", userId)
                .getResultList()
                .stream().findFirst();
    }

    @Override
    @Transactional
    public Cart save(Cart cart) {
        if (cart.getId() == null) {
            em.persist(cart);
            return cart;
        }
        return em.merge(cart);
    }
}
