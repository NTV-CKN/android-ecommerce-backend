package com.example.pkcn.controller.cart;

import com.example.pkcn.controller.advice.cus_exception.UserNotExistException;
import com.example.pkcn.dto.response.BadgeCartDTO;
import com.example.pkcn.dto.response.CartDTO;
import com.example.pkcn.dto.request.CartLocalDTO;
import com.example.pkcn.dto.response.UserProfileDTO;
import com.example.pkcn.entity.User;
import com.example.pkcn.repository.user.user_detail_repo.IUserRepository;
import com.example.pkcn.service.cart.ICartService;
import com.example.pkcn.service.user.profile.IUserProfileService;
import com.example.pkcn.service.user.user_detail_service_impl.UserDetailServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    private final ICartService cartService;
    private final IUserProfileService profileService;

    public CartController(ICartService cartService, IUserProfileService profileService) {
        this.cartService = cartService;
        this.profileService = profileService;
    }

    @GetMapping
    public ResponseEntity<CartDTO> getCart(@AuthenticationPrincipal UserDetails userDetails) throws UserNotExistException {
        // Gọi hàm service để lấy hoặc tự tạo mới nếu chưa có
        UserProfileDTO user = profileService.getUserProfile(userDetails.getUsername());
        Integer userId = user.getId();
        CartDTO cartDTO = cartService.getCart(userId);
        return ResponseEntity.ok(cartDTO);
    }

    @PutMapping("/items/{itemId}")
    public ResponseEntity<CartDTO> updateQuantity(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Integer itemId,
            @RequestParam Integer qty) throws UserNotExistException {
        UserProfileDTO user = profileService.getUserProfile(userDetails.getUsername());
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
            @PathVariable Integer itemId) throws UserNotExistException {
        UserProfileDTO user = profileService.getUserProfile(userDetails.getUsername());
        Integer userId = user.getId();
        CartDTO updatedCart = cartService.removeItem(userId, itemId);
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/items")
    public ResponseEntity<CartDTO> clearCart(@AuthenticationPrincipal UserDetails userDetails) throws UserNotExistException {
        UserProfileDTO user = profileService.getUserProfile(userDetails.getUsername());
        Integer userId = user.getId();
        CartDTO updatedCart = cartService.deleteAll(userId);
        return ResponseEntity.ok(updatedCart);
    }

    @GetMapping("/count")
    public ResponseEntity<BadgeCartDTO> getCount(@AuthenticationPrincipal UserDetails userDetails) throws UserNotExistException {
        UserProfileDTO user = profileService.getUserProfile(userDetails.getUsername());
        Integer userId = user.getId();
        Long count = cartService.countTotalVariant(userId);
        return ResponseEntity.ok(new BadgeCartDTO(count));
    }
    @PostMapping("/add")
    public ResponseEntity<BadgeCartDTO> addToCart(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CartLocalDTO request) throws UserNotExistException {
        UserProfileDTO user = profileService.getUserProfile(userDetails.getUsername());
        Integer userId = user.getId();
        BadgeCartDTO badgeCartDTO = cartService.addToCart(userId, request);
        return ResponseEntity.ok(badgeCartDTO);
    }
}