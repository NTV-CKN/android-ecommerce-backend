package com.example.pkcn.repository.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("""
        SELECT new com.example.pkcn.dto.response.FeatureProductDTO(
            p.id,
            p.name,
            p.subtitle,
            p.minPrice,
            MAX(pi.urlImage),
            AVG(r.numStar))
        FROM Product p
        LEFT JOIN p.images pi
        LEFT JOIN p.reviews r
        WHERE p.status = 1
            AND (pi IS NULL OR (pi.isMain = true AND pi.productVariant IS NULL))
            AND (r IS NULL OR r.status = 'ACTIVE')
        GROUP BY p.id, p.name, p.subtitle, p.minPrice
        ORDER BY AVG(r.numStar) DESC, COUNT(DISTINCT r.id) DESC
""")
    List<FeatureProductDTO> findFeatureProduct(Pageable pageable);
}
