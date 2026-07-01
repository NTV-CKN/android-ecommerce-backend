package com.example.pkcn.repository.admin.voucher;

import com.example.pkcn.common.DiscountType;
import com.example.pkcn.dto.response.VoucherAdminDTO;
import com.example.pkcn.entity.Voucher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Entity;
import java.util.List;

@Repository
public class AdminVoucherRepositoryImpl implements IAdminVoucherRepository {

    EntityManager em;

    public AdminVoucherRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public Voucher findVoucherById(Integer id) {
        return em.find(Voucher.class, id);
    }

    @Override
    public List<VoucherAdminDTO> findAdminVoucher(String typeCode, DiscountType discountType, String keyword) {
        StringBuilder jpql = new StringBuilder(
                "SELECT new com.example.pkcn.dto.response.VoucherAdminDTO(" +
                        "v.id, v.code, v.title, v.discountType, " +
                        "v.discountValue, v.minPriceAllow, v.startDate, v.endDate, " +
                        "v.usageLimit, v.usedCount, v.status, " +
                        "v.voucherType.id, v.voucherType.code, v.voucherType.name) " +
                        "FROM Voucher v " +
                        "WHERE LOWER(v.code) LIKE LOWER(CONCAT('%', :keyword, '%'))"
        );

        if (typeCode != null) {
            jpql.append(" AND v.voucherType.code = :typeCode");
        }
        if (discountType != null) {
            jpql.append(" AND v.discountType = :discountType");
        }

        jpql.append(" ORDER BY v.createdAt DESC");

        TypedQuery<VoucherAdminDTO> query = em.createQuery(jpql.toString(), VoucherAdminDTO.class);
        query.setParameter("keyword", keyword != null ? keyword : "");
        if (typeCode != null) query.setParameter("typeCode", typeCode);
        if (discountType != null) query.setParameter("discountType", discountType);

        return query.getResultList();
    }

    @Override
    public void save(Voucher voucher) {
        em.persist(voucher);
    }

    @Override
    public void update(Voucher voucher) {
        em.merge(voucher);
    }

    @Override
    public void deleteById(Integer id) {
        Voucher v = em.find(Voucher.class, id);
        if(v != null) {
            em.remove(v);
        }
    }
}
