package com.example.pkcn.service.product;

import com.example.pkcn.controller.advice.cus_exception.DataNotFoundException;
import com.example.pkcn.dto.response.*;
import com.example.pkcn.repository.product.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureProductServiceImpl implements IProductService {

    private IProductRepository productRepository;

    public FeatureProductServiceImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<FeatureProductDTO> getFeatureProduct(Integer categoryId, int limit) {
        return productRepository.findFeatureProduct(categoryId, limit);
    }


    @Override
    public ProductDetailsDTO getProductDetails(int id) {
        ProductDetailsDTO productDTO = productRepository.findProductById(id);
        if (productDTO == null) {
            throw new DataNotFoundException("Không tìm thấy sản phẩm với ID: " + id);
        }
        productDTO.setImages(productRepository.findImagesProductById(id));
        productDTO.setProductVariants(productRepository.findProductVariantById(id));
        List<ReviewDTO> reviews = productRepository.findReviewById(id);
        productDTO.setReviews(reviews);
        List<RelatedProductDTO> relateProduct = productRepository.findRelateProductById(id, 4);
        productDTO.setRelateProducts(relateProduct);
        return productDTO;
    }

    @Override
    public List<FeatureProductDTO> searchProduct(String keyword){return productRepository.searchProduct(keyword);}

    @Override
    public PageResponseDTO<FeatureProductDTO> getProductByCategory(
            Integer categoryId,
            Integer page,
            Integer pageSize,
            Double minPrice,
            Double maxPrice,
            String sortBy,
            String keyword,
            String direction
    ) {
        return productRepository.findProductByCategory(
                categoryId,
                page,
                pageSize,
                minPrice,
                maxPrice,
                sortBy,
                keyword,
                direction
        );
    }


}
