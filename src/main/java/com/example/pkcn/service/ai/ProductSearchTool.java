package com.example.pkcn.service.ai;

import com.example.pkcn.dto.request.ai.ProductSearchRequest;
import com.example.pkcn.dto.response.ai.ProductChatSummaryDTO;
import com.example.pkcn.dto.response.ai.ProductSearchResponse;
import com.example.pkcn.utils.TempChatContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
public class ProductSearchTool implements Function<ProductSearchRequest, ProductSearchResponse> {
    private final EntityManager em;

    @Autowired
    public ProductSearchTool(EntityManager em) {
        this.em = em;
    }

    @Override
    public ProductSearchResponse apply(ProductSearchRequest request) {
        StringBuilder jpql = new StringBuilder(
                "SELECT DISTINCT p.id, p.name, p.minPrice, " +
                        "(SELECT pi.urlImage FROM ProductImage pi WHERE pi.product.id = p.id AND pi.isMain = true), p.stock " +
                        "FROM Product p LEFT JOIN p.variants v WHERE p.status = 1 "
        );

        if (request.keyword() != null && !request.keyword().isBlank()) {
            jpql.append("AND (LOWER(p.name) LIKE LOWER(:keyword) OR LOWER(p.description) LIKE LOWER(:keyword)) ");
        }

        if (request.maxPrice() != null) {
            jpql.append("AND p.minPrice <= :maxPrice ");
        }

        if (request.color() != null && !request.color().isBlank()) {
            jpql.append("AND LOWER(v.color) LIKE LOWER(:color) ");
        }

        Query query = em.createQuery(jpql.toString());

        if (request.keyword() != null && !request.keyword().isBlank()) {
            query.setParameter("keyword", "%" + request.keyword().trim() + "%");
        }
        if (request.maxPrice() != null) {
            query.setParameter("maxPrice", request.maxPrice());
        }

        if (request.color() != null && !request.color().isBlank()) {
            query.setParameter("color", "%" + request.color().trim() + "%");
        }

        query.setMaxResults(5);

        List<Object[]> results = query.getResultList();
        List<ProductChatSummaryDTO> dtoList = new ArrayList<>();

        for (Object[] row : results) {
            dtoList.add(new ProductChatSummaryDTO(
                    (Integer) row[0],
                    (String) row[1],
                    (java.math.BigDecimal) row[2],
                    (String) row[3],
                    (Integer) row[4]
            ));
        }

        TempChatContext.setProducts(dtoList);
        return new ProductSearchResponse(dtoList);
    }
}