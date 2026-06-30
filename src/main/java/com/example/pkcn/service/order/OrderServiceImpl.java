package com.example.pkcn.service.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.OrderManageDTO;
import com.example.pkcn.repository.order.IOrderRepository;
import org.springframework.stereotype.Service;

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
}