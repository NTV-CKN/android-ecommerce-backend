package com.example.pkcn.service.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.PageResponseDTO;

import com.example.pkcn.dto.response.OrderManageDTO;

import java.util.List;

public interface IOrderService {

    public List<OrderHistoryDTO> getOrderHistory(
            Integer userId,
            String status,
            int offset,
            int limit
    );
 PageResponseDTO<OrderHistoryDTO> findOrderHistory(Integer userId, String status, Integer page, Integer pageSize);

    public List<OrderManageDTO> getAllOrders(
            String status,
            String keyword
    );
    public void updateOrderStatus(
            Integer orderId,
            String status
    );
}
