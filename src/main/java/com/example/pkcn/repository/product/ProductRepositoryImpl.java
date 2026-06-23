package com.example.pkcn.repository.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.dto.response.ProductDetailsDTO;
import com.example.pkcn.entity.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryImpl implements IProductRepository {

    private EntityManager em;

    public ProductRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<FeatureProductDTO> findFeatureProduct(int limit) {
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
                "WHERE p.status = 1 " +
                "AND (pi IS NULL OR (pi.isMain = true AND pi.productVariant IS NULL)) " +
                "AND (r IS NULL OR r.status = 'ACTIVE') " +
                "GROUP BY p.id, p.name, p.subtitle, p.minPrice " +
                "HAVING AVG(r.numStar) >= 4.0" +
                "ORDER BY AVG(r.numStar) DESC, COUNT(DISTINCT r.id) DESC";

        TypedQuery<FeatureProductDTO> query = em.createQuery(sql, FeatureProductDTO.class);
        query.setMaxResults(limit);
        return query.getResultList();

    }

    @Override
    public ProductDetailsDTO findProductById(int id) {
        String sql = "SELECT new com.example.pkcn.dto.response.ProductDetailsDTO(" +
                "p.id, " +
                "p.name, " +
                "p.subtitle, " +
                "p.description, " +
                "p.warrantyPeriod, " +
                "p.minPrice, " +
                "p.maxPrice, " +
                "MAX(pi.urlImage)) " +
                "FROM Product p " +
                "LEFT JOIN p.images pi " +
                "WHERE p.id = :id " +
                "AND (pi IS NULL OR (pi.isMain = true AND pi.productVariant IS NULL)) " +
                "GROUP BY p.id, p.name, p.subtitle, p.description, p.warrantyPeriod, p.minPrice, p.maxPrice, p.stock";


            TypedQuery<ProductDetailsDTO> query = em.createQuery(sql, ProductDetailsDTO.class);
            query.setParameter("id", id);
            return query.getSingleResult();
    }

}
