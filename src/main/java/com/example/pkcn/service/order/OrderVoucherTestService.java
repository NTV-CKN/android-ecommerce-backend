package com.example.pkcn.service.order;

import com.example.pkcn.dto.response.VoucherDTO;
import com.example.pkcn.dto.response.VoucherTypeDTO;
import com.example.pkcn.common.DiscountType;
import com.example.pkcn.entity.Order;
import com.example.pkcn.utils.VoucherJsonUtil;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderVoucherTestService {

    private final EntityManager em;

    public OrderVoucherTestService(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public void insertFakeVoucherToOrder(Integer orderId) {

        /*
         * Voucher ship
         */
        VoucherDTO voucher1 =
                new VoucherDTO(
                        1,
                        "SHIPFREE",
                        "Free Ship",
                        DiscountType.FIXED_AMOUNT,
                        new BigDecimal("30000"),
                        BigDecimal.ZERO,
                        LocalDateTime.now(),
                        1,
                        "SHIP",
                        "Shipping Voucher"
                );

        /*
         * Voucher giảm đơn hàng
         */
        VoucherDTO voucher2 =
                new VoucherDTO(
                        2,
                        "SALE50K",
                        "Giảm 50K",
                        DiscountType.FIXED_AMOUNT,
                        new BigDecimal("50000"),
                        new BigDecimal("300000"),
                        LocalDateTime.now(),
                        2,
                        "ORDER",
                        "Order Voucher"
                );

        /*
         * test 2 voucher
         */
        List<VoucherDTO> vouchers =
                new ArrayList<>();

        vouchers.add(voucher1);
        vouchers.add(voucher2);

        /*
         * convert -> json
         */
        String json =
                VoucherJsonUtil.toJson(vouchers);

        /*
         * update order
         */
        Order order =
                em.find(Order.class, orderId);

        order.setAppliedVouchers(json);

        em.merge(order);
    }
}