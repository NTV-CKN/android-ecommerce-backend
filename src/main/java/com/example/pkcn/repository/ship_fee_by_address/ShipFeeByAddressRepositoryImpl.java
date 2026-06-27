package com.example.pkcn.repository.ship_fee_by_address;

import com.example.pkcn.entity.ShipFeeByAddress;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
@Transactional
public class ShipFeeByAddressRepositoryImpl implements IShipFeeByAddressRepository {
    private EntityManager em;

    @Autowired
    public ShipFeeByAddressRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<ShipFeeByAddress> getShipFeeByAddresses() {
        String sql = "SELECT a FROM ShipFeeByAddress a";
        TypedQuery<ShipFeeByAddress> query = em.createQuery(sql, ShipFeeByAddress.class);

        return query.getResultList();
    }

    @Override
    public Boolean checkMatchProvinceCity(String provinceCity) {
        String sql = """
                SELECT s
                FROM ShipFeeByAddress s
                WHERE s.provinceCity = :provinceCity
                """;

        TypedQuery<ShipFeeByAddress> query = em.createQuery(sql, ShipFeeByAddress.class);
        query.setParameter("provinceCity", provinceCity);

        return query.getSingleResultOrNull() != null;
    }

    @Override
    public ShipFeeByAddress getShipFeeByProvinceCity(String provinceCity) {
        String sql = """
                SELECT s
                FROM ShipFeeByAddress s
                WHERE s.provinceCity = :provinceCity
                """;

        TypedQuery<ShipFeeByAddress> query = em.createQuery(sql, ShipFeeByAddress.class);
        query.setParameter("provinceCity", provinceCity);

        return query.getSingleResultOrNull();
    }
}
