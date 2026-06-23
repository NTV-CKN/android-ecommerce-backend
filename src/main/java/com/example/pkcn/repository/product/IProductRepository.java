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

public interface IProductRepository {
    List<FeatureProductDTO> findFeatureProduct(int limit);

    List<FeatureProductDTO> searchProduct(String keyword);
}
