package com.example.pkcn.service.admin.product;

import com.example.pkcn.dto.ProductAdminPageDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;

public interface IProductAdminService {
    PageResponseDTO<ProductAdminPageDTO> getProducts(String keyWord, Integer page, Integer pageSize);

    String generateUniqueSku(String productName, String color, String size);

    SuccessBasicDTO saveProduct(ProductAdminPageDTO productAdminPageDTO);
}
