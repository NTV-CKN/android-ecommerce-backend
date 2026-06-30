package com.example.pkcn.repository.admin.product;

import com.example.pkcn.dto.ProductAdminPageDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ProductAdminRepositoryImpl implements IProductAdminRepository{
    private EntityManager em;

    @Autowired
    public ProductAdminRepositoryImpl(EntityManager em) {
        this.em = em;
    }


    @Override
    public long getTotalElementByKeywordAndNameCategory(String keyWord, String nameCategory) {
        //Lọc các sản phẩm theo keyWord và thể loại, lấy ds các biến thể + ảnh cho nó
        String jpql1 = """
            SELECT DISTINCT p 
            FROM Product p
            LEFT JOIN FETCH p.variants v
            LEFT JOIN FETCH v.productImage vi
            JOIN p.productCategories pc
            JOIN pc.category c
            WHERE (:keyword IS NULL OR LOWER(p.name) LIKE LOWER(:keyword) OR p.id = :keyWord)
              AND (:categoryName IS NULL OR LOWER(c.categoryName) = LOWER(:categoryName))
            ORDER BY p.id DESC
        """;

        return em.createQuery(jpql1, Long.class)
                .setParameter("keyword", keyWord != null ? "%" + keyWord + "%" : null)
                .setParameter("categoryName", nameCategory)
                .getSingleResult();
    }

    @Override
    public PageResponseDTO<ProductAdminPageDTO> getProducts(String keyWord, String nameCategory, Integer page, Integer pageSize) {
        return null;
    }


}
