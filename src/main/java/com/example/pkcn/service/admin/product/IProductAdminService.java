package com.example.pkcn.service.admin.product;

import com.example.pkcn.dto.ProductAdminPageDTO;
import com.example.pkcn.dto.response.PageResponseDTO;

public interface IProductAdminService {
    PageResponseDTO<ProductAdminPageDTO> getProducts(String keyWord, Integer page, Integer pageSize);
}
