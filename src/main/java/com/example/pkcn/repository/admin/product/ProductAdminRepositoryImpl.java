package com.example.pkcn.repository.admin.product;

import com.example.pkcn.entity.Product;
import com.example.pkcn.entity.ProductVariant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
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
    public long getTotalElementByKeywordAndNameCategory(String keyWord) {
        //Lọc các sản phẩm theo keyWord và thể loại, lấy ds các biến thể + ảnh cho nó
        String jpql1 = """
        SELECT COUNT(DISTINCT p) 
        FROM Product p
        WHERE (:keyword IS NULL OR :keyword = '' OR LOWER(p.name) LIKE LOWER(:keyword) OR CAST(p.id AS string) = :rawKeyword)
          
    """;

        String processedKeyword = (keyWord != null && !keyWord.trim().isEmpty()) ? "%" + keyWord.trim() + "%" : null;
        String rawKeyword = (keyWord != null && !keyWord.trim().isEmpty()) ? keyWord.trim() : null;

        return em.createQuery(jpql1, Long.class)
                .setParameter("keyword", processedKeyword)
                .setParameter("rawKeyword", rawKeyword)
                .getSingleResult();
    }

    @Override
    public List<Product> getProducts(String keyWord, Integer page, Integer pageSize) {
        //Lọc các sản phẩm theo keyWord và thể loại, lấy ds các biến thể + ảnh cho nó
        String jpql1 = """
            SELECT DISTINCT p 
            FROM Product p
            LEFT JOIN FETCH p.variants v
            LEFT JOIN FETCH v.productImage vi
            
            WHERE (:keyWord IS NULL OR LOWER(p.name) LIKE LOWER(:keyWord))
           
            ORDER BY p.id DESC
        """;

        int firstResult = (page - 1) * pageSize;
        List<Product> products = em.createQuery(jpql1, Product.class)
                .setParameter("keyWord", keyWord != null ? "%" + keyWord + "%" : null)
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

    @Override
    public boolean existsBySku(String finalSku) {
        String sql = """
                SELECT pv
                FROM ProductVariant pv
                WHERE pv.sku = :sku
                """;

        TypedQuery query = em.createQuery(sql, ProductVariant.class);

        return query.setParameter("sku", finalSku)
                .getSingleResultOrNull() != null;
    }
}
