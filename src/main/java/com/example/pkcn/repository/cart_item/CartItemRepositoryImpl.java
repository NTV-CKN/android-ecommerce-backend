package com.example.pkcn.repository.cart_item;

import com.example.pkcn.entity.CartItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CartItemRepositoryImpl implements ICartItemRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<CartItem> findByCardId(Integer cartId) {
        String query = "SELECT i FROM CartItem i WHERE i.cart.id = :cartId";
        return em.createQuery(query, CartItem.class)
                .setParameter("cartId", cartId)
                .getResultList();
    }

    @Override
    public Optional<CartItem> findByCartIdAndProductVariantId(Integer cartId, Integer productVariantId) {
        String query = "SELECT i FROM CartItem i " +
                "WHERE i.cart.id = :cartId " +
                "AND i.productVariantId = :productVariantId";
        return em.createQuery(query, CartItem.class)
                .setParameter("cartId", cartId)
                .setParameter("productVariantId", productVariantId)
                .getResultList()
                .stream().findFirst();
    }

    @Override
    public void deleteAllById(Integer cartId) {
        String query = "DELETE FROM CartItem i WHERE i.cart.id = :cartId";
        em.createQuery(query)
                .setParameter("cartId", cartId)
                .executeUpdate();
    }

    @Transactional
    @Override
    public CartItem save(CartItem item) {
        if (item.getId() == null) {
            em.persist(item);
            return item;
        }
        return em.merge(item);
    }

    @Transactional
    @Override
    public void delete(CartItem item) {
        em.remove(em.contains(item) ? item : em.merge(item));
    }

    public Long countVariantByUserId(Integer userId) {
        String query = "SELECT COUNT(ci) FROM CartItem ci WHERE ci.cart.userId = :userId";
        try {
            Long count = (Long) em.createQuery(query)
                    .setParameter("userId", userId)
                    .getSingleResult();
            return count != null ? count : 0L;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }
}
