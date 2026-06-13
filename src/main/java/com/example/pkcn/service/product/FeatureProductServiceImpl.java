package com.example.pkcn.service.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.repository.product.IProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
