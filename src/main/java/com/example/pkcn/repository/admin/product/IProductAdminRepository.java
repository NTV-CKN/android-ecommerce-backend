package com.example.pkcn.repository.admin.product;

import com.example.pkcn.dto.ProductAdminPageDTO;
import com.example.pkcn.dto.response.PageResponseDTO;

public interface IProductAdminRepository {
    long getTotalElementByKeywordAndNameCategory(String keyWord, String nameCategory);
    PageResponseDTO<ProductAdminPageDTO> getProducts(String keyWord, String nameCategory,
                                                     Integer page, Integer pageSize);
}
