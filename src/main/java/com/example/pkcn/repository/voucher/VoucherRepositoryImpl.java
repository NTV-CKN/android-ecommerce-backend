package com.example.pkcn.repository.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.response.VoucherDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class VoucherRepositoryImpl implements IVoucherRepository {
    private EntityManager em;

    public VoucherRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<VoucherDTO> findVouchers(String typeCode, DiscountType discountType, String keyword) {
        StringBuilder jpql = new StringBuilder(
                "SELECT new com.example.pkcn.dto.response.VoucherDTO(" +
                        "v.id, v.code, v.title, v.discountType, " +
                        "v.discountValue, v.minPriceAllow, v.endDate, " +
                        "v.voucherType.id, v.voucherType.code, v.voucherType.name) " +
                        "FROM Voucher v " +
                        "WHERE v.status = 1 " +
                        "AND v.startDate <= :now " +
                        "AND v.endDate >= :now " +
                        "AND v.usedCount < v.usageLimit " +
                        "AND LOWER(v.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
        );

        // Lọc theo loại voucher (MAIN_ORDER / SHIPPING) - chip "Giảm phí ship", "Giảm theo đơn hàng"
        if (typeCode != null) {
            jpql.append(" AND v.voucherType.code = :typeCode");
        }

        // Lọc theo hình thức giảm (FIXED_AMOUNT / PERCENTAGE) - chip "Giảm tiền mặt", "Giảm theo %"
        if (discountType != null) {
            jpql.append(" AND v.discountType = :discountType");
        }

        jpql.append(" ORDER BY v.endDate ASC");

        TypedQuery<VoucherDTO> query = em.createQuery(jpql.toString(), VoucherDTO.class);
        query.setParameter("now", LocalDateTime.now());
        query.setParameter("keyword", keyword != null ? keyword : "");
        if (typeCode != null) query.setParameter("typeCode", typeCode);
        if (discountType != null) query.setParameter("discountType", discountType);
        query.setMaxResults(20);

        return query.getResultList();
    }
}
