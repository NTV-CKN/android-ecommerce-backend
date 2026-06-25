package com.example.pkcn.repository.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements IProductRepository {

    private EntityManager em;

    public ProductRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<FeatureProductDTO> findFeatureProduct(Integer categoryId, int limit) {
        String sql = "SELECT new com.example.pkcn.dto.response.FeatureProductDTO(" +
                "p.id, " +
                "p.name, " +
                "p.subtitle, " +
                "p.minPrice, " +
                "MAX(pi.urlImage), " +
                "AVG(r.numStar)) " +
                "FROM Product p " +
                "LEFT JOIN p.images pi " +
                "LEFT JOIN p.reviews r " +
                "LEFT JOIN p.category c " +
                "WHERE p.status = 1 " +
                "AND (:categoryId IS NULL OR c.id = :categoryId) " +
                "AND (pi IS NULL OR (pi.isMain = true AND pi.productVariant IS NULL)) " +
                "AND (r IS NULL OR r.status = 'ACTIVE') " +
                "GROUP BY p.id, p.name, p.subtitle, p.minPrice " +
                "ORDER BY AVG(r.numStar) DESC, COUNT(DISTINCT r.id) DESC";

        TypedQuery<FeatureProductDTO> query = em.createQuery(sql, FeatureProductDTO.class);
        query.setParameter("categoryId", categoryId);
        query.setMaxResults(limit);
        return query.getResultList();

    }

    @Override
    public List<FeatureProductDTO> searchProduct(
            String keyword
    ) {
        String sql =
                "SELECT new com.example.pkcn.dto.response.FeatureProductDTO(" +
                        "p.id, " +
                        "p.name, " +
                        "p.subtitle, " +
                        "p.minPrice, " +
                        "MAX(pi.urlImage), " +
                        "AVG(r.numStar)) " +

                        "FROM Product p " +
                        "LEFT JOIN p.images pi " +
                        "LEFT JOIN p.reviews r " +

                        "WHERE p.status = 1 " +

                        "AND LOWER(p.name) " +
                        "LIKE LOWER(CONCAT('%', :keyword, '%')) " +

                        "AND (pi IS NULL OR " +
                        "(pi.isMain = true " +
                        "AND pi.productVariant IS NULL)) " +

                        "GROUP BY p.id, " +
                        "p.name, " +
                        "p.subtitle, " +
                        "p.minPrice";
        TypedQuery<FeatureProductDTO> query =
                em.createQuery(
                        sql,
                        FeatureProductDTO.class
                );
        query.setParameter(
                "keyword",
                keyword
        );
        query.setMaxResults(10);
        return query.getResultList();
    }
}
