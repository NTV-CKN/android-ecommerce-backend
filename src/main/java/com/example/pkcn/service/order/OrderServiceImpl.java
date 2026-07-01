package com.example.pkcn.service.order;

import com.example.pkcn.dto.request.OrderRequestDTO;
import com.example.pkcn.dto.response.OrderDetailsHistoryDTO;
import com.example.pkcn.dto.response.OrderDetailAdminDTO;
import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.OrderManageDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.entity.*;
import com.example.pkcn.repository.order.IOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    private final IOrderRepository orderRepository;
    private final EntityManager em;
    public OrderServiceImpl(
            IOrderRepository orderRepository, EntityManager em
    ) {
        this.orderRepository = orderRepository;
        this.em = em;
    }

//   @Override
//    public PageResponseDTO<OrderHistoryDTO> findOrderHistory(Integer userId, String status, Integer page, Integer pageSize) {
//        return orderRepository.findOrderHistoryById(userId, status, page, pageSize);
//    }

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
    public PageResponseDTO<OrderManageDTO> getAllOrders(
            int page,
            int limit,
            String status,
            String keyword
    ) {

        return orderRepository.findAllOrders(
                page,
                limit,
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

    @Override
    public OrderDetailAdminDTO getOrderDetailById(
            Integer orderId
    ) {
        return orderRepository.getOrderDetailById(
                orderId
        );
    }

    @Override
    @Transactional
    public void createOrder(Integer userId, OrderRequestDTO requestDTO) {
        User user = em.find(User.class, userId);
        UserAddress userAddress = em.find(UserAddress.class, requestDTO.getAddressOrderId());
        if (user == null || userAddress == null) {
            throw new RuntimeException("Thông tin tài khoản hoặc địa chỉ nhận hàng không tồn tại!");
        }

        AddressOrder addressOrder = new AddressOrder();
        addressOrder.setReceiverName(userAddress.getReceiverName());
        addressOrder.setPhoneNumber(userAddress.getPhoneNumber());
        addressOrder.setAddressDetail(userAddress.getAddressDetail());
        addressOrder.setProvinceCity(userAddress.getProvinceCity());
        em.persist(addressOrder);

        Order order = new Order();
        order.setUser(user);
        order.setAddressOrder(addressOrder);
        order.setPaymentMethodId(requestDTO.getPaymentMethodId());
        order.setNote(requestDTO.getNote());
        BigDecimal shippingFee = orderRepository.getShippingFeeByAddressId(addressOrder.getId());
        order.setShippingFee(shippingFee);
        if (requestDTO.getAppliedVouchers() != null && !requestDTO.getAppliedVouchers().isEmpty()) {
            try {
                order.setAppliedVouchers(new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(requestDTO.getAppliedVouchers()));
            } catch (Exception e) {
                order.setAppliedVouchers("[]");
            }
        }
        BigDecimal totalProductAmount = BigDecimal.ZERO;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(OrderRequestDTO.ProductItem item : requestDTO.getProducts()) {
            int currentStock = orderRepository.getProductStock(item.getProductVariantId());
            if(currentStock < item.getQuantity()) {
                throw new RuntimeException("Sản phẩm mã số " + item.getProductVariantId() + " đã hết hoặc không đủ hàng trong kho!");
            }
            orderRepository.updateProductStock(item.getProductVariantId(), item.getQuantity());
            ProductVariant variant = em.find(ProductVariant.class, item.getProductVariantId());
            if (variant == null) {
                throw new RuntimeException("Sản phẩm mã số " + item.getProductVariantId() + " không tồn tại!");
            }
            BigDecimal itemPrice = variant.getPrice();
            totalProductAmount = totalProductAmount.add(itemPrice.multiply(BigDecimal.valueOf(item.getQuantity())));

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProductVariant(variant);
            detail.setQuantity(item.getQuantity());
            detail.setPriceTotal(itemPrice);
            orderDetails.add(detail);
        }
        order.setTotalMustPay(totalProductAmount.add(order.getShippingFee()));
        order.setOrderDetails(orderDetails);
        orderRepository.saveOrder(order);
        for(OrderDetail orderDetail : orderDetails) {
            orderRepository.deleteCartItem(userId, orderDetail.getProductVariant().getId());
        }
    }
}