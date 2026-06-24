package com.example.pkcn.repository.product;

import com.example.pkcn.dto.response.*;
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
                "p.id, p.name, p.subtitle, p.description, p.warrantyPeriod, p.minPrice, p.maxPrice) " +
                "FROM Product p WHERE p.id = :id";
        return em.createQuery(sql, ProductDetailsDTO.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<String> findImagesProductById(int id) {
        String sql = "SELECT pi.urlImage FROM ProductImage  pi WHERE pi.product.id = :id AND pi.productVariant IS NULL ORDER BY pi.isMain DESC, pi.id ASC";
       TypedQuery<String> query = em.createQuery(sql, String.class);
       query.setParameter("id", id);
       return query.getResultList();
    }

    @Override
    public List<ProductVariantDTO> findProductVariantById(int id) {
        String sql = "SELECT new com.example.pkcn.dto.response.ProductVariantDTO(v.id, v.sku, v.name, v.price, v.stock, v.color, v.size, v.gram, (SELECT MAX(pi2.urlImage) FROM ProductImage pi2 WHERE pi2.productVariant = v)) FROM ProductVariant v WHERE v.product.id = :id";
       TypedQuery<ProductVariantDTO> query = em.createQuery(sql, ProductVariantDTO.class);
       query.setParameter("id", id);
       return query.getResultList();
    }

    @Override
    public List<ReviewDTO> findReviewById(int id) {
        String sql = "SELECT new com.example.pkcn.dto.response.ReviewDTO(" +
                "r.id, r.name, r.numStar, r.evaluate, " +
                "cast(FUNCTION('DATE_FORMAT', r.evaluateDate, '%d/%m/%Y %H:%i') as string)) " +
                "FROM Review r WHERE r.product.id = :id AND r.status = 'ACTIVE' ORDER BY r.id DESC";
        TypedQuery<ReviewDTO> query = em.createQuery(sql, ReviewDTO.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<RelatedProductDTO> findRelateProductById(int id, int limit) {
        String sql = "SELECT new com.example.pkcn.dto.response.RelatedProductDTO(\n" +
                "    p.id, \n" +
                "    p.name, \n" +
                "    p.minPrice, \n" +
                "    MAX(pi.urlImage), \n" +
                "    AVG(r.numStar)" +
                ")\n" +
                "FROM Product p\n" +
                "LEFT JOIN p.images pi\n" +
                "LEFT JOIN p.reviews r\n" +
                "WHERE p.id IN (\n" +
                "    SELECT pc2.product.id FROM ProductCategory pc2 WHERE pc2.category.id IN (\n" +
                "        SELECT pc.category.id FROM ProductCategory pc WHERE pc.product.id = :id\n" +
                "    )\n" +
                ") AND p.id != :id\n" +
                "AND (pi IS NULL OR (pi.isMain = true AND pi.productVariant IS NULL))\n" +
                "AND (r IS NULL OR r.status = 'ACTIVE')\n" +
                "GROUP BY p.id, p.name, p.minPrice";
        TypedQuery<RelatedProductDTO> query = em.createQuery(sql, RelatedProductDTO.class);
        query.setParameter("id", id);
        query.setMaxResults(limit);
        return query.getResultList();
    }
}
