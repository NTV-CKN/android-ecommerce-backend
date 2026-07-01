package com.example.pkcn.service.order;

import com.example.pkcn.dto.request.OrderRequestDTO;
import com.example.pkcn.dto.response.OrderDetailsHistoryDTO;
import com.example.pkcn.dto.response.OrderDetailAdminDTO;
import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.OrderManageDTO;
import com.example.pkcn.dto.response.PageResponseDTO;

import java.util.List;

public interface IOrderService {

    List<OrderHistoryDTO> getOrderHistory(
            Integer userId,
            String status,
            int offset,
            int limit
    );

    PageResponseDTO<OrderHistoryDTO> findOrderHistory(
            Integer userId,
            String status,
            Integer page,
            Integer pageSize
    );

    PageResponseDTO<OrderManageDTO> getAllOrders(
            int page,
            int limit,
            String status,
            String keyword
    );

    void updateOrderStatus(
            Integer orderId,
            String status
    );
    public void cancelOrder(
            Integer orderId,
            Integer userId
    );
    List<OrderDetailsHistoryDTO> getOrderDetails(Integer orderId, Integer userId);

    OrderDetailAdminDTO getOrderDetailById(
            Integer orderId
    );
    void createOrder(Integer userId, OrderRequestDTO requestDTO);
}