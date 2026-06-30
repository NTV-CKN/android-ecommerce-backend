package com.example.pkcn.repository.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;

import java.util.List;

public interface IOrderRepository {
    public List<OrderHistoryDTO> findAllOrderHistory(Integer userId, String status, int offset, int limit);
}
