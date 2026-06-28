package com.example.pkcn.service.cart;

import com.example.pkcn.dto.request.CartLocalDTO;
import com.example.pkcn.dto.response.BadgeCartDTO;
import com.example.pkcn.dto.response.CartDTO;
import com.example.pkcn.dto.response.CartItemDTO;
import com.example.pkcn.entity.Cart;
import com.example.pkcn.entity.CartItem;
import com.example.pkcn.entity.ProductVariant;
import com.example.pkcn.repository.cart.ICartRepository;
import com.example.pkcn.repository.cart_item.ICartItemRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements ICartService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    ICartRepository cartRepository;

    @Autowired
    ICartItemRepository itemRepository;

    @Override
    public Cart getOrCreateCart(Integer userId) {
        return cartRepository.findCartByUserIdAndIsValid(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setValid(true);
                    newCart.setTotalPrice(BigDecimal.ZERO);
                    newCart.setCreatedAt(LocalDateTime.now());
                    newCart.setUpdatedAt(LocalDateTime.now());
                    return cartRepository.save(newCart);
                });
    }

    @Override
    public CartDTO getCart(Integer userId) {
        Cart cart = getOrCreateCart(userId);
        List<CartItem> items = itemRepository.findByCartId(cart.getId());
        return mapToDTO(cart, items);
    }

    @Transactional
    @Override
    public CartDTO updateQuantity(Integer userId, Integer itemId, Integer qty) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = em.find(CartItem.class, itemId);

        if (item == null) throw new RuntimeException("Item không tồn tại");

        if (qty <= 0) {
            itemRepository.delete(item);
        } else {
            String priceQuery = "SELECT pv.price FROM ProductVariant pv WHERE pv.id = :variantId";
            BigDecimal unitPrice = em.createQuery(priceQuery, BigDecimal.class)
                    .setParameter("variantId", item.getProductVariantId())
                    .getSingleResult();

            item.setQuantity(qty);
            item.setPriceTotal(unitPrice.multiply(BigDecimal.valueOf(qty)));
            item.setUpdatedAt(LocalDateTime.now());

            itemRepository.save(item);
        }

        recalculateTotal(cart);

        return getCart(userId);
    }
    @Transactional
    @Override
    public CartDTO deleteAll(Integer userId) {
        Cart cart = getOrCreateCart(userId);
        itemRepository.deleteAllById(cart.getId());
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);
        return getCart(userId);
    }

    @Override
    public Long countTotalVariant(Integer userId) {
        return itemRepository.countVariantByUserId(userId);
    }

    @Override
    public BadgeCartDTO addToCart(Integer userId, CartLocalDTO request) {
        if(request == null){
            return  new BadgeCartDTO(countTotalVariant(userId));
        }
        List<CartLocalDTO> list = List.of(request);
        this.mergeLocalCart(userId, list);
        Long totalCount = this.countTotalVariant(userId);
        return new BadgeCartDTO(totalCount);
    }

    @Override
    public CartDTO removeItem(Integer userId, Integer itemId) {
        Cart cart = getOrCreateCart(userId);
        CartItem item = em.find(CartItem.class, itemId);
        if (item != null) {
            itemRepository.delete(item);
            recalculateTotal(cart);
        }
        return getCart(userId);
    }

    @Override
    @Transactional // Đảm bảo tính toàn vẹn dữ liệu khi chạy chuỗi lệnh insert/update
    public CartDTO mergeLocalCart(Integer userId, List<CartLocalDTO> localList) {

        // 1. TỐI ƯU: Lấy giỏ hàng ra TRƯỚC VÒNG LẶP (Chỉ gọi DB 1 lần duy nhất)
        Cart cart = getOrCreateCart(userId);

        if (localList == null || localList.isEmpty()) {
            return getCart(userId);
        }

        // 2. TỐI ƯU: Gom tất cả ProductVariantId để lấy giá tiền chính xác từ DB
        List<Integer> variantIds = localList.stream()
                .map(CartLocalDTO::getProductVariantId)
                .distinct()
                .toList();

        String priceQuery = "SELECT pv.id, pv.price FROM ProductVariant pv WHERE pv.id IN :variantIds";
        List<Object[]> priceResults = em.createQuery(priceQuery, Object[].class)
                .setParameter("variantIds", variantIds)
                .getResultList();

        Map<Integer, BigDecimal> priceMap = priceResults.stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],
                        row -> (BigDecimal) row[1]
                ));

        // 3. TỐI ƯU: Lấy toàn bộ danh sách items hiện tại đang có trong DB của giỏ hàng này
        // Chuyển thành Map để kiểm tra xem sản phẩm đã tồn tại chưa với tốc độ O(1) thay vì gọi repository liên tục
        List<CartItem> currentDbItems = itemRepository.findByCartId(cart.getId());
        Map<Integer, CartItem> dbItemMap = currentDbItems.stream()
                .collect(Collectors.toMap(CartItem::getProductVariantId, item -> item));

        // 4. Bắt đầu duyệt danh sách local để merge
        for (CartLocalDTO local : localList) {
            // Lấy giá chuẩn từ DB, nếu không tìm thấy mặc định là 0
            BigDecimal unitPrice = priceMap.getOrDefault(local.getProductVariantId(), BigDecimal.ZERO);

            // Kiểm tra xem biến thể sản phẩm này đã có trong giỏ hàng DB chưa
            CartItem item = dbItemMap.get(local.getProductVariantId());

            if (item != null) {
                // Trường hợp 1: Đã có sản phẩm này trong giỏ -> Cộng dồn số lượng
                int newQty = item.getQuantity() + local.getQuantity();
                item.setQuantity(newQty);
                item.setPriceTotal(unitPrice.multiply(BigDecimal.valueOf(newQty))); // Tính lại tổng tiền bằng giá DB
                item.setUpdatedAt(LocalDateTime.now());
                itemRepository.save(item);
            } else {
                // Trường hợp 2: Chưa có sản phẩm này -> Tạo mới item
                CartItem newItem = new CartItem();
                newItem.setCart(cart);
                newItem.setProductVariantId(local.getProductVariantId());
                newItem.setQuantity(local.getQuantity());
                // Bỏ dòng setUnitPrice() vì Entity không có trường này
                newItem.setPriceTotal(unitPrice.multiply(BigDecimal.valueOf(local.getQuantity())));
                newItem.setCreatedAt(LocalDateTime.now());
                newItem.setUpdatedAt(LocalDateTime.now());
                itemRepository.save(newItem);

                // Đẩy ngược newItem vào Map tạm để đề phòng danh sách localList truyền lên bị trùng lặp id
                dbItemMap.put(local.getProductVariantId(), newItem);
            }
        }

        // 5. TỐI ƯU: Tính lại tổng tiền của cả giỏ hàng DUY NHẤT 1 LẦN sau khi kết thúc vòng lặp
        recalculateTotal(cart);

        return getCart(userId);
    }
    private void recalculateTotal(Cart cart) {
        List<CartItem> items = itemRepository.findByCartId(cart.getId());
        BigDecimal total = items.stream()
                .map(CartItem::getPriceTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
        cart.setUpdatedAt(LocalDateTime.now());
        cartRepository.save(cart);
    }

    private CartDTO mapToDTO(Cart cart, List<CartItem> items) {
        CartDTO dto = new CartDTO();
        dto.setId(cart.getId());
        dto.setTotalPrice(cart.getTotalPrice());

        if (items == null || items.isEmpty()) {
            dto.setCartItems(new ArrayList<>());
            return dto;
        }

        List<Integer> variantIds = items.stream()
                .map(CartItem::getProductVariantId)
                .distinct()
                .toList();

        String query = "SELECT pv.id, pv.name, p.name, pi.urlImage, pv.price " +
                "FROM ProductVariant pv " +
                "JOIN pv.product p " +
                "LEFT JOIN p.images pi ON pi.isMain = true AND pi.productVariant IS NULL " +
                "WHERE pv.id IN :variantIds";

        List<Object[]> queryResults = em.createQuery(query, Object[].class)
                .setParameter("variantIds", variantIds)
                .getResultList();


        Map<Integer, Object[]> variantDataMap = queryResults.stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],
                        row -> row
                ));

        List<CartItemDTO> itemDTOs = items.stream().map(item -> {
            CartItemDTO itemDTO = new CartItemDTO();
            itemDTO.setId(item.getId());
            itemDTO.setProductVariantId(item.getProductVariantId());
            itemDTO.setQuantity(item.getQuantity());
            itemDTO.setTotalPrice(item.getPriceTotal());

            Object[] data = variantDataMap.get(item.getProductVariantId());

            if (data != null) {
                itemDTO.setProductVariantName(data[1] != null ? data[1].toString() : "");
                itemDTO.setProductName(data[2] != null ? data[2].toString() : "");
                itemDTO.setProductImage(data[3] != null ? data[3].toString() : "");
                itemDTO.setUnitPrice(data[4] != null ? (BigDecimal) data[4] : BigDecimal.ZERO);
            } else {
                itemDTO.setUnitPrice(BigDecimal.ZERO);
            }

            return itemDTO;
        }).toList();

        dto.setCartItems(itemDTOs);
        return dto;
    }
}
