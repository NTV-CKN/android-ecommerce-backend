package com.example.pkcn.repository.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class OrderRepositoryImpl implements IOrderRepository {
    private EntityManager em;
    public OrderRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<OrderHistoryDTO> findAllOrderHistory(Integer userId, String status, int offset, int limit) {
        String sql = "SELECT new com.example.pkcn.dto.response.OrderHistoryDTO("+
                "o.id, " +
                "o.orderDate, " +
                "o.deliveryDate, " +
                "o.shippingFee, " +
                "o.totalMustPay, " +
                "o.statusOrder, " +
                "o.note, " +
                "ao.receiverName, " +
                "ao.addressDetail, " +
                "ao.provinceCity) " +
                "FROM Order o " +
                "JOIN o.addressOrder ao " +
                "WHERE o.user.id = :userId ";
        boolean hasStatus = status != null && !status.isEmpty();
        if(hasStatus){
            sql += "AND o.statusOrder = :status ";
        }
        sql += "ORDER BY o.orderDate DESC";
        TypedQuery<OrderHistoryDTO> query = em.createQuery(sql, OrderHistoryDTO.class);
        query.setParameter("userId", userId);
        if (hasStatus) {
            query.setParameter("status", status);
        }
        query.setFirstResult(offset);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
