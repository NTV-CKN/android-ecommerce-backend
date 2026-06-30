package com.example.pkcn.service.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.PageResponseDTO;

public interface IOrderService {
    PageResponseDTO<OrderHistoryDTO> findOrderHistory(Integer userId, String status, Integer page, Integer pageSize);
}
