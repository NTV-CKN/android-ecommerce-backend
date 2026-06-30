package com.example.pkcn.repository.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class OrderRepositoryImpl implements IOrderRepository {
    private EntityManager em;
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
}
