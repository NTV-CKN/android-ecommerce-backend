package com.example.pkcn.repository.order;

import com.example.pkcn.dto.response.OrderDetailsHistoryDTO;
import com.example.pkcn.dto.response.OrderDetailAdminDTO;
import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.dto.response.OrderManageDTO;
import com.example.pkcn.entity.Order;

import java.math.BigDecimal;
import java.util.List;

public interface IOrderRepository {
    PageResponseDTO<OrderHistoryDTO> findOrderHistoryById(Integer userId,String status, Integer page, Integer pageSize);
    public List<OrderHistoryDTO> findAllOrderHistory(Integer userId, String status, int offset, int limit);

    PageResponseDTO<OrderManageDTO> findAllOrders(
            int page,
            int limit,
            String status,
            String keyword
    );

    void updateOrderStatus(
            Integer orderId,
            String status
    );

    void cancelOrder(Integer orderId, Integer userId);

    List<OrderDetailsHistoryDTO> getOrderDetails(Integer orderId, Integer userId);

    OrderDetailAdminDTO getOrderDetailById(
            Integer orderId
    );
    void saveOrder(Order order);
    int getProductStock(Integer productVariantId);
    void updateProductStock(Integer productVariantId, int quantityToSubtract);
    void deleteCartItem(Integer userId, Integer productVariantId);
    BigDecimal getShippingFeeByAddressId(Integer addressOrderId);
}
