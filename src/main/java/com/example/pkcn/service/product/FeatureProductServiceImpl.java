package com.example.pkcn.service.product;

import com.example.pkcn.controller.advice.cus_exception.DataNotFoundException;
import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.dto.response.ProductDetailsDTO;
import com.example.pkcn.dto.response.RelatedProductDTO;
import com.example.pkcn.dto.response.ReviewDTO;
import com.example.pkcn.entity.Product;
import com.example.pkcn.repository.product.IProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FeatureProductServiceImpl implements IProductService {

    private IProductRepository productRepository;

    public FeatureProductServiceImpl(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<FeatureProductDTO> getFeatureProduct(int limit) {
        return productRepository.findFeatureProduct(limit);
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
}
