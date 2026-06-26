package com.example.pkcn.service.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.dto.response.ProductDetailsDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    public List<FeatureProductDTO> getFeatureProduct(Integer categoryId, int limit);

    public ProductDetailsDTO getProductDetails(int id);
    public List<FeatureProductDTO> searchProduct(String keyword);

    public PageResponseDTO<FeatureProductDTO> getProductByCategory(
            Integer categoryId,
            Integer page,
            Integer pageSize,
            Double minPrice,
            Double maxPrice,
            String sortBy,
            String keyword,
            String direction
    );
}
