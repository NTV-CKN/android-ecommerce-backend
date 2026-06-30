package com.example.pkcn.service.admin.product;

import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.entity.Product;

public interface IProductAdminService {
    PageResponseDTO<Product> getProducts(String keyWord, Integer page, Integer pageSize, String nameCategory);
}
