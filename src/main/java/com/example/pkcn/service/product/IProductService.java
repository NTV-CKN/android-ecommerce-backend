package com.example.pkcn.service.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.dto.response.ProductDetailsDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    public List<FeatureProductDTO> getFeatureProduct(int limit);

    public ProductDetailsDTO getProductDetails(int id);
}
