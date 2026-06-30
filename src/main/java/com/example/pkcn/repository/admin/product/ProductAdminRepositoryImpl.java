package com.example.pkcn.repository.admin.product;

import com.example.pkcn.entity.Product;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

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
        SELECT COUNT(DISTINCT p) 
        FROM Product p
        JOIN p.productCategories pc
        JOIN pc.category c
        WHERE (:keyword IS NULL OR :keyword = '' OR LOWER(p.name) LIKE LOWER(:keyword) OR CAST(p.id AS string) = :rawKeyword)
          AND (:categoryName IS NULL OR :categoryName = '' OR LOWER(c.categoryName) = LOWER(:categoryName))
    """;

        String processedKeyword = (keyWord != null && !keyWord.trim().isEmpty()) ? "%" + keyWord.trim() + "%" : null;
        String rawKeyword = (keyWord != null && !keyWord.trim().isEmpty()) ? keyWord.trim() : null;
        String processedCategory = (nameCategory != null && !nameCategory.trim().isEmpty()) ? nameCategory.trim() : null;

        return em.createQuery(jpql1, Long.class)
                .setParameter("keyword", processedKeyword)
                .setParameter("rawKeyword", rawKeyword)
                .setParameter("categoryName", processedCategory)
                .getSingleResult();
    }

    @Override
    public List<Product> getProducts(String keyWord, String nameCategory, Integer page, Integer pageSize) {
        //Lọc các sản phẩm theo keyWord và thể loại, lấy ds các biến thể + ảnh cho nó
        String jpql1 = """
            SELECT DISTINCT p 
            FROM Product p
            LEFT JOIN FETCH p.variants v
            LEFT JOIN FETCH v.productImage vi
            JOIN p.productCategories pc
            JOIN pc.category c
            WHERE (:keyWord IS NULL OR LOWER(p.name) LIKE LOWER(:keyWord))
              AND (:categoryName IS NULL OR LOWER(c.categoryName) = LOWER(:categoryName))
            ORDER BY p.id DESC
        """;

        int firstResult = (page - 1) * pageSize;
        List<Product> products = em.createQuery(jpql1, Product.class)
                .setParameter("keyWord", keyWord != null ? "%" + keyWord + "%" : null)
                .setParameter("categoryName", nameCategory)
                .setFirstResult(firstResult)
                .setMaxResults(pageSize)
                .getResultList();

        if (products.isEmpty()) {
            return products;
        }

        String jpql2 = """
            SELECT DISTINCT p 
            FROM Product p
            LEFT JOIN FETCH p.images
            WHERE p IN :products
        """;

        products = em.createQuery(jpql2, Product.class)
                .setParameter("products", products)
                .getResultList();

        return products;
    }
}
