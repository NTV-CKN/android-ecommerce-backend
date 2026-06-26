package com.example.pkcn.controller.cart;

import com.example.pkcn.dto.response.BadgeCartDTO;
import com.example.pkcn.dto.response.CartDTO;
import com.example.pkcn.dto.request.CartLocalDTO;
import com.example.pkcn.entity.User;
import com.example.pkcn.repository.user.user_detail_repo.IUserRepository;
import com.example.pkcn.service.cart.ICartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final ICartService cartService;
    private final IUserRepository userRepository;

    public CartController(ICartService cartService, IUserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        // Gọi hàm service để lấy hoặc tự tạo mới nếu chưa có
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        Integer userId = user.getId();
        CartDTO cartDTO = cartService.getCart(userId);
        return ResponseEntity.ok(cartDTO);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartDTO> updateQuantity(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer itemId,
            @RequestParam Integer qty) {
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        Integer userId = user.getId();
        CartDTO updatedCart = cartService.updateQuantity(userId, itemId, qty);
        return ResponseEntity.ok(updatedCart);
    }

//    @PostMapping("/merge")
//    public ResponseEntity<CartDTO> mergeLocalCart(
//            @PathVariable Integer userId,
//            @RequestBody List<CartLocalDTO> localList) {
//
//        CartDTO mergedCart = cartService.mergeLocalCart(userId, localList);
//        return ResponseEntity.ok(mergedCart);
//    }

    @DeleteMapping("/items/{itemId}")
    public ResponseEntity<CartDTO> deleteItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer itemId) {
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        Integer userId = user.getId();
        CartDTO updatedCart = cartService.removeItem(userId, itemId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/items")
    public ResponseEntity<CartDTO> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        Integer userId = user.getId();
        CartDTO updatedCart = cartService.deleteAll(userId);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/count")
    public ResponseEntity<BadgeCartDTO> getCount(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        Integer userId = user.getId();
        Long count = cartService.countTotalVariant(userId);
        return ResponseEntity.ok(new BadgeCartDTO(count));
    }
    @PostMapping("/add")
    public ResponseEntity<BadgeCartDTO> addToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartLocalDTO request) {
        User user = userRepository.findUserByEmail(userDetails.getUsername());
        Integer userId = user.getId();
        BadgeCartDTO badgeCartDTO = cartService.addToCart(userId, request);
        System.out.println("===== ĐÃ VÀO CONTROLLER =====");
        return ResponseEntity.ok(badgeCartDTO);
    }
}