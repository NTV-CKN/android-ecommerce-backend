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

    Product findProductById(Integer id);

    void deleteProductCategoriesByProductId(Integer id);

    void deleteMainImageByProductId(Integer id);

    void deleteSubImagesByProductId(Integer id);

    ProductVariant findVariantBySkuAndProductId(String sku, Integer id);

    ProductVariant findVariantBySku(String sku);

    void deleteImageByVariantId(Integer id);

    void deleteVariant(ProductVariant variantToDelete);
}
