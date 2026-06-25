package com.example.pkcn.service.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    public List<FeatureProductDTO> getFeatureProduct(Integer categoryId, int limit);

    public List<FeatureProductDTO> searchProduct(String keyword);
}
