package com.example.pkcn.repository.order;

import com.example.pkcn.dto.response.OrderDetailsHistoryDTO;
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

import com.example.pkcn.dto.response.OrderDetailAdminDTO;
import com.example.pkcn.dto.response.OrderItemDTO;
import com.example.pkcn.dto.response.VoucherDTO;
import com.example.pkcn.entity.Order;
import com.example.pkcn.utils.VoucherJsonUtil;


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
        if (status != null && !status.isEmpty()) {
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
        if (status != null && !status.isEmpty()) {
            sqlCount += " AND o.statusOrder = :status ";
        }
        TypedQuery<Long> queryCount = em.createQuery(sqlCount, Long.class);
        queryCount.setParameter("userId", userId);
        if (status != null && !status.isEmpty()) {
            queryCount.setParameter("status", status);
        }
        Long count = queryCount.getSingleResult();
        return new PageResponseDTO<>(list, page, pageSize, count);
    }

    @Override
    public List<OrderHistoryDTO> findAllOrderHistory(Integer userId, String status, int offset, int limit) {
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

        if (status != null && !status.isEmpty()) {
            sql += " AND o.statusOrder = :status ";
        }
        sql += " ORDER BY o.orderDate DESC";

        TypedQuery<OrderHistoryDTO> query = em.createQuery(sql, OrderHistoryDTO.class);
        query.setParameter("userId", userId);

        if (status != null && !status.isEmpty()) {
            query.setParameter("status", status);
        }

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public PageResponseDTO<OrderManageDTO> findAllOrders(
            int page,
            int limit,
            String status,
            String keyword
    ) {

        int offset = (page - 1) * limit;

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

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        List<OrderManageDTO> items =
                query.getResultList();

        String countSql =
                "SELECT COUNT(o.id) FROM Order o WHERE 1=1 ";

        if (hasStatus) {
            countSql += " AND o.statusOrder = :status ";
        }

        if (hasKeyword) {
            countSql += " AND CAST(o.id as string) LIKE :keyword ";
        }

        TypedQuery<Long> countQuery =
                em.createQuery(
                        countSql,
                        Long.class
                );

        if (hasStatus) {
            countQuery.setParameter(
                    "status",
                    status
            );
        }

        if (hasKeyword) {
            countQuery.setParameter(
                    "keyword",
                    "%" + keyword + "%"
            );
        }

        Long totalElements =
                countQuery.getSingleResult();

        return new PageResponseDTO<>(
                items,
                page,
                limit,
                totalElements
        );
    }

    @Override
    public OrderDetailAdminDTO getOrderDetailById(
            Integer orderId
    ) {

        String orderSql = """

    SELECT new com.example.pkcn.dto.response.OrderDetailAdminDTO(

        o.id,

        o.user.fullName,

        o.user.email,

        o.totalMustPay,

        o.shippingFee,

        o.statusOrder,

        o.note,

        o.paymentMethodId,

        o.orderDate,

        null,

        null

    )

    FROM Order o

    WHERE o.id = :orderId

    """;

        String itemSql = """

    SELECT new com.example.pkcn.dto.response.OrderItemDTO(

        pv.product.name,

        od.quantity,

        od.priceTotal

    )

    FROM OrderDetail od

    JOIN od.productVariant pv

    WHERE od.order.id = :orderId

    """;

        OrderDetailAdminDTO orderInfo =
                em.createQuery(
                                orderSql,
                                OrderDetailAdminDTO.class
                        )
                        .setParameter(
                                "orderId",
                                orderId
                        )
                        .getSingleResult();

        List<OrderItemDTO> items =
                em.createQuery(
                                itemSql,
                                OrderItemDTO.class
                        )
                        .setParameter(
                                "orderId",
                                orderId
                        )
                        .getResultList();

        Order order =
                em.find(
                        Order.class,
                        orderId
                );

        List<VoucherDTO> vouchers =
                VoucherJsonUtil.fromJson(
                        order.getAppliedVouchers()
                );

        return new OrderDetailAdminDTO(

                orderInfo.getOrderId(),

                orderInfo.getCustomerName(),

                orderInfo.getCustomerEmail(),

                orderInfo.getTotalPrice(),

                orderInfo.getShippingFee(),

                orderInfo.getStatus(),

                orderInfo.getNote(),

                orderInfo.getPaymentMethodId(),

                orderInfo.getOrderDate(),

                items,

                vouchers
        );
    }

    @Transactional
    @Override
    public void updateOrderStatus(
            Integer orderId,
            String status
    ) {

        String sql =
                """
            
                UPDATE Order o
                            SET o.statusOrder = :
                status
            WHERE o.id = :orderId
            """;

        em.createQuery(sql)
                .setParameter("status", status)
                .setParameter(

                        "orderId", orderId)
                .executeUpdate();
    }
    @Transactional
    @Override
    public void cancelOrder(
            Integer orderId
            , Integer userId) {
        String sql = """
        UPDATE Order o
        SET o.statusOrder = 'cancel'
        WHERE o.id = :orderId
        AND o.user.id = :userId
        AND o.statusOrder IN ('pending_approve', 'approve_by_admin')
        """;

        int update = em.createQuery(sql)
                .setParameter("orderId", orderId)
                .setParameter("userId", userId)
                .executeUpdate();

        if (update > 0) {
            restoreProductStock(orderId);
        } else {
            throw new RuntimeException("Không thể hủy đơn hàng! Đơn hàng không tồn tại hoặc không trong trạng thái cho phép.");
        }
    }

    @Override
    public List<OrderDetailsHistoryDTO> getOrderDetails(Integer orderId, Integer userId) {
        String sql = """
                SELECT new com.example.pkcn.dto.response.OrderDetailsHistoryDTO(
                od.id,
                o.id,
                od.price,
                od.productVariantId,
                od.quantity,
                pv.name,
                COALESCE(pi.urlImage, ''))
                FROM OrderDetail od
                JOIN od.order o
                LEFT JOIN ProductVariant pv ON pv.id = od.productVariantId
                LEFT JOIN pv.productImage pi
                WHERE o.id = :orderId
                AND o.user.id = :userId
                ORDER BY od.id
                """;
        TypedQuery<OrderDetailsHistoryDTO> query = em.createQuery(sql, OrderDetailsHistoryDTO.class);
        query.setParameter("orderId", orderId);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Transactional
    protected void restoreProductStock(Integer orderId) {
        String sql = """
        UPDATE product_variants pv
        JOIN order_details od  ON od.product_variant_id = pv.id
        SET pv.stock = pv.stock + od.quantity
        WHERE od.order_id = :orderId
        """;
        em.createNativeQuery(sql)
                .setParameter("orderId", orderId)
                .executeUpdate();
    }


}