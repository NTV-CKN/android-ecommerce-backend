package com.example.pkcn.repository.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.OrderManageDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class OrderRepositoryImpl implements IOrderRepository {
    private final EntityManager em;
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

    @Override
    public List<OrderManageDTO> findAllOrders(
            String status,
            String keyword
    ) {

        String sql = """
            SELECT new com.example.pkcn.dto.response.OrderManageDTO(
                o.id,
                o.user.email,
                o.totalMustPay,
                o.statusOrder,
                o.orderDate
            )
            FROM Order o
            WHERE 1=1
            """;

        boolean hasStatus =
                status != null && !status.isEmpty();

        boolean hasKeyword =
                keyword != null && !keyword.isEmpty();


        if (hasStatus) {
            sql += " AND o.statusOrder = :status ";
        }

        if (hasKeyword) {
            sql += " AND CAST(o.id as string) LIKE :keyword ";
        }

        sql += " ORDER BY o.orderDate DESC ";

        TypedQuery<OrderManageDTO> query =
                em.createQuery(
                        sql,
                        OrderManageDTO.class
                );

        if (hasStatus) {
            query.setParameter(
                    "status",
                    status
            );
        }

        if (hasKeyword) {
            query.setParameter(
                    "keyword",
                    "%" + keyword + "%"
            );
        }

        return query.getResultList();
    }

    @Transactional
    @Override
    public void updateOrderStatus(
            Integer orderId,
            String status
    ) {

        String sql = """
            UPDATE Order o
            SET o.statusOrder = :status
            WHERE o.id = :orderId
            """;

        em.createQuery(sql)
                .setParameter("status", status)
                .setParameter("orderId", orderId)
                .executeUpdate();
    }
}
