package com.example.pkcn.repository.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import com.example.pkcn.dto.response.OrderManageDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class OrderRepositoryImpl implements IOrderRepository {
    private final EntityManager em;
    public OrderRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public PageResponseDTO<OrderHistoryDTO> findOrderHistoryById(Integer userId, String status, Integer page, Integer pageSize) {
        String sql = "SELECT new com.example.pkcn.dto.response.OrderHistoryDTO(" +
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
        if(status != null && !status.isEmpty()){
            sql += " AND o.statusOrder = :status ";
        }
        sql += " ORDER BY o.orderDate DESC";
        TypedQuery<OrderHistoryDTO> query = em.createQuery(sql, OrderHistoryDTO.class);
        query.setParameter("userId", userId);
        if (status != null && !status.isEmpty()) {
        query.setParameter("status", status);
        }
        int offset = (page - 1) * pageSize;
        query.setFirstResult(offset);
        query.setMaxResults(pageSize);
        List<OrderHistoryDTO> list = query.getResultList();

        String sqlCount = "SELECT COUNT(o) FROM Order o WHERE o.user.id = :userId ";
        if(status != null && !status.isEmpty()) {
            sqlCount += " AND o.statusOrder = :status ";
        }
        TypedQuery<Long> queryCount = em.createQuery(sqlCount, Long.class);
        queryCount.setParameter("userId", userId);
        if (status != null && !status.isEmpty()) {
            queryCount.setParameter("status", status);
        }
        Long count = queryCount.getSingleResult();
        return new PageResponseDTO<>(list,page,pageSize,count);
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
