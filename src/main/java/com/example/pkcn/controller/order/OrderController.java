package com.example.pkcn.controller.order;

import com.example.pkcn.dto.response.OrderHistoryDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.entity.User;
import com.example.pkcn.repository.user.user_detail_repo.IUserRepository;
import com.example.pkcn.service.order.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/orders")
public class OrderController {
    private IOrderService orderService;
    private IUserRepository userRepository;

    public OrderController(IOrderService orderService, IUserRepository userRepository) {
        this.orderService = orderService;
        this.userRepository = userRepository;
    }
    @GetMapping("/history")
    public ResponseEntity<PageResponseDTO<OrderHistoryDTO>> findOrderHistory(@AuthenticationPrincipal UserDetails userDetails, @RequestParam(required = false) String status, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(orderService.findOrderHistory(user.getId(), status, page, pageSize));
    }
}
