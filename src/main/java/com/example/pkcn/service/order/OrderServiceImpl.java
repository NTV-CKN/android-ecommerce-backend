package com.example.pkcn.service.order;

import com.example.pkcn.dto.response.OrderDetailsHistoryDTO;
import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.repository.order.IOrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.example.pkcn.dto.response.OrderManageDTO;
import com.example.pkcn.repository.order.IOrderRepository;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {

    private final IOrderRepository orderRepository;

    public OrderServiceImpl(
            IOrderRepository orderRepository
    ) {
        this.orderRepository = orderRepository;
    }
    @Override
    public List<OrderHistoryDTO> getOrderHistory(
            Integer userId,
            String status,
            int offset,
            int limit
    ) {
        return orderRepository.findAllOrderHistory(
                userId,
                status,
                offset,
                limit
        );
    }

    @Override
    public PageResponseDTO<OrderHistoryDTO> findOrderHistory(Integer userId, String status, Integer page, Integer pageSize) {
        return orderRepository.findOrderHistoryById(userId, status, page, pageSize);
    }


    @Override
    public List<OrderManageDTO> getAllOrders(
            String status,
            String keyword
    ) {
        return orderRepository.findAllOrders(
                status,
                keyword
        );
    }

    @Override
    public void updateOrderStatus(
            Integer orderId,
            String status
    ) {
        orderRepository.updateOrderStatus(
                orderId,
                status
        );
    }

    @Override
    @Transactional
    public void cancelOrder(Integer orderId, Integer userId) {
        orderRepository.cancelOrder(orderId, userId);
    }

    @Override
    public List<OrderDetailsHistoryDTO> getOrderDetails(Integer orderId, Integer userId) {
        return orderRepository.getOrderDetails(orderId, userId);
    }
}
