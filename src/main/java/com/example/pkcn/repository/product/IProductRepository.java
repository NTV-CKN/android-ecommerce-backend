package com.example.pkcn.repository.product;

import com.example.pkcn.dto.response.*;

import java.util.List;

public interface IProductRepository {
    List<FeatureProductDTO> findFeatureProduct(Integer categoryId, int limit);
    ProductDetailsDTO findProductById(int id);
    List<String> findImagesProductById(int id);
    List<ProductVariantDTO> findProductVariantById(int id);
    List<ReviewDTO> findReviewById(int id);
    List<RelatedProductDTO> findRelateProductById(int id, int limit);
    List<FeatureProductDTO> searchProduct(String keyword);
    PageResponseDTO<FeatureProductDTO> findProductByCategory(
            Integer categoryId,
            Integer page,
            Integer pageSize,
            Double minPrice,
            Double maxPrice,
            String sortBy,
            String direction,
            String keyword
    );
}
