package com.example.pkcn.service.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.repository.product.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeatureProductServiceImpl implements IProductService {

    final ProductRepository productRepository;

    public FeatureProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<FeatureProductDTO> getFeatureProduct(Pageable pageable) {
        PageRequest page = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
        return productRepository.findFeatureProduct(page);
    }
}
