package com.example.pkcn.repository.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.PageResponseDTO;

import java.util.List;

public interface IOrderRepository {
    PageResponseDTO<OrderHistoryDTO> findOrderHistoryById(Integer userId,String status, Integer page, Integer pageSize);
}
