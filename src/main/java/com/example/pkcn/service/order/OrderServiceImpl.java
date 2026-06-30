package com.example.pkcn.service.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.repository.order.IOrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;
    public OrderServiceImpl(IOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    @Override
    public PageResponseDTO<OrderHistoryDTO> findOrderHistory(Integer userId, String status, Integer page, Integer pageSize) {
        return orderRepository.findOrderHistoryById(userId, status, page, pageSize);
    }
}
