package com.example.pkcn.repository.admin.product;

import com.example.pkcn.entity.Product;

import java.util.List;

public interface IProductAdminRepository {
    long getTotalElementByKeywordAndNameCategory(String keyWord);
    List<Product> getProducts(String keyWord,
                              Integer page, Integer pageSize);
}
