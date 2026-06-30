package com.example.pkcn.controller.admin;

import com.example.pkcn.dto.request.UpdateOrderStatusRequest;
import com.example.pkcn.dto.response.OrderDetailAdminDTO;
import com.example.pkcn.dto.response.OrderManageDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.service.order.IOrderService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin-order")
public class OrderController_Admin {

    private final IOrderService orderService;

    public OrderController_Admin(
            IOrderService orderService
    ) {
        this.orderService = orderService;
    }

    /*
     * GET ALL ORDERS WITH PAGINATION
     */
    @GetMapping("/manage")
    public ResponseEntity<PageResponseDTO<OrderManageDTO>> getAllOrders(

            @RequestParam(defaultValue = "1")
            int page,
            @RequestParam(defaultValue = "5")
            int limit,
            @RequestParam(required = false)
            String status,
            @RequestParam(required = false)
            String keyword
    ) {
        PageResponseDTO<OrderManageDTO> response =
                orderService.getAllOrders(
                        page,
                        limit,
                        status,
                        keyword
                );
        System.out.println(
                "CURRENT PAGE = " + response.getCurrentPage()
        );
        System.out.println(
                "TOTAL PAGES = " + response.getTotalPages()
        );
        return ResponseEntity.ok(
                response
        );
    }

    /*
     * UPDATE ORDER STATUS
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateOrderStatus(

            @PathVariable("id")
            Integer orderId,

            @RequestBody
            UpdateOrderStatusRequest request
    ) {

        orderService.updateOrderStatus(
                orderId,
                request.getStatus()
        );

        return ResponseEntity.ok().build();
    }

    /*
     * GET ORDER DETAIL
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailAdminDTO> getOrderDetail(

            @PathVariable("id")
            Integer orderId
    ) {

        return ResponseEntity.ok(

                orderService.getOrderDetailById(
                        orderId
                )
        );
    }
}