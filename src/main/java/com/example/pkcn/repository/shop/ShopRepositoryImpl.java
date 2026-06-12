package com.example.pkcn.repository.shop;

import com.example.pkcn.entity.Shop;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;


@Repository
@Primary
@Transactional
public class ShopRepositoryImpl implements IShopRepository {
    private final EntityManager em;

    @Autowired
    public ShopRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public Shop getShopInfo() {
        String sql = "SELECT s FROM Shop s WHERE s.status = true";
        TypedQuery<Shop> query =  em.createQuery(sql, Shop.class);

        return query.getResultList().getFirst();
    }
}
