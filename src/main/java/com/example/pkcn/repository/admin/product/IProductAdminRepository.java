package com.example.pkcn.repository.admin.product;

import com.example.pkcn.entity.Product;
import com.example.pkcn.entity.ProductCategory;
import com.example.pkcn.entity.ProductImage;
import com.example.pkcn.entity.ProductVariant;

import java.util.List;

public interface IProductAdminRepository {
    long getTotalElementByKeywordAndNameCategory(String keyWord);
    List<Product> getProducts(String keyWord,
                              Integer page, Integer pageSize);

    boolean existsBySku(String finalSku);

    Product saveProduct(Product product);

    ProductVariant saveProductVariant(ProductVariant productVariant);

    void saveProductImages(List<ProductImage> images);

    void saveProductCategory(ProductCategory category);
}
