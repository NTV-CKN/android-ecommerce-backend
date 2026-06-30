package com.example.pkcn.controller;

import com.example.pkcn.dto.request.UpdateOrderStatusRequest;
import com.example.pkcn.dto.response.OrderManageDTO;
import com.example.pkcn.service.order.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin-order")
public class OrderController_Admin {

    final IOrderService orderService;

    public OrderController_Admin(
            IOrderService orderService
    ) {
        this.orderService = orderService;
    }


    @GetMapping("/manage")
    public ResponseEntity<List<OrderManageDTO>> getAllOrders(

            @RequestParam(required = false)
            String status,

            @RequestParam(required = false)
            String keyword
    ) {

        List<OrderManageDTO> list =
                orderService.getAllOrders(
                        status,
                        keyword
                );

        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> updateOrderStatus(

            @PathVariable("id")
            Integer orderId,

            @RequestBody
            UpdateOrderStatusRequest request
    ) {

        orderService.updateOrderStatus(
                orderId,
                request.getStatus()
        );

        return ResponseEntity.ok(
                "Update success"
        );
    }
}