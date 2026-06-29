package com.example.pkcn.repository.voucher;

import com.example.pkcn.dto.response.VoucherDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

public class VoucherRepositoryImpl implements IVoucherRepository {
    private EntityManager em;
    public VoucherRepositoryImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public List<VoucherDTO> findVoucherByType(Integer typeId, String keyword) {
        String sql =
                "SELECT new com.example.pkcn.dto.response.VoucherDTO(" +
                        "v.id, " +
                        "v.code, " +
                        "v.title, " +
                        "v.discountType, " +
                        "v.discountValue, " +
                        "v.minPriceAllow, " +
                        "v.endDate) " +
                        "FROM Voucher v " +
                        "WHERE v.voucherType.id = :voucherTypeId " +
                        "AND v.status = 1 " +
                        "AND v.startDate <= :now " +
                        "AND v.endDate >= :now " +
                        "AND v.usedCount < v.usageLimit " +
                        "AND LOWER(v.code) " +
                        "LIKE LOWER(CONCAT('%', :keyword, '%')) " +

                        "ORDER BY v.endDate ASC";
        TypedQuery<VoucherDTO> query = em.createQuery(sql, VoucherDTO.class);
        query.setParameter("voucherTypeId", typeId);
        query.setParameter("now", LocalDateTime.now());
        query.setParameter("keyword", keyword != null ? keyword : "");
        query.setMaxResults(20);

        return  query.getResultList();
    }
}
