package com.example.pkcn.dto;

import com.example.pkcn.dto.response.CategoriesDTO;
import com.example.pkcn.dto.response.ProductVariantDTO;
import com.example.pkcn.entity.Product;
import com.example.pkcn.entity.ProductCategory;
import com.example.pkcn.entity.ProductImage;
import com.example.pkcn.entity.ProductVariant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductAdminPageDTO {
    private Integer id;
    private String folderId;
    private String name;
    private Integer warrantyPeriod;
    private String subtitle;
    private String description;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Boolean status;
    private Integer stock;
    private String mainImage;

    private List<ProductVariantDTO> productVariantDTOS;
    private List<String> images;
    private List<CategoriesDTO> categoriesDTOS;

    public ProductAdminPageDTO() {
    }

    public ProductAdminPageDTO(Integer id, String folderId, String name,
                               Integer warrantyPeriod, String subtitle,
                               String description, BigDecimal minPrice,
                               BigDecimal maxPrice, Boolean status, Integer stock,
                               String mainImage
    ) {
        this.mainImage = mainImage;
        this.id = id;
        this.folderId = folderId;
        this.name = name;
        this.warrantyPeriod = warrantyPeriod;
        this.subtitle = subtitle;
        this.description = description;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.status = status;
        this.stock = stock;

        this.categoriesDTOS = new ArrayList<>();
        this.productVariantDTOS = new ArrayList<>();
        this.images = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFolderId() {
        return folderId;
    }

    public void setFolderId(String folderId) {
        this.folderId = folderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(Integer warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public List<ProductVariantDTO> getProductVariantDTOS() {
        return productVariantDTOS;
    }

    public void setProductVariantDTOS(List<ProductVariantDTO> productVariantDTOS) {
        this.productVariantDTOS = productVariantDTOS;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<CategoriesDTO> getCategoriesDTOS() {
        return categoriesDTOS;
    }

    public void setCategoriesDTOS(List<CategoriesDTO> categoriesDTOS) {
        this.categoriesDTOS = categoriesDTOS;
    }

    public void initData(Product product) {
        if (product == null) return;

        this.id = product.getId();
        this.folderId = product.getFolderId();
        this.name = product.getName();
        this.warrantyPeriod = product.getWarrantyPeriod();
        this.subtitle = product.getSubtitle();
        this.description = product.getDescription();
        this.minPrice = product.getMinPrice();
        this.maxPrice = product.getMaxPrice();
        this.stock = product.getStock();

        this.status = (product.getStatus() != null && product.getStatus() == 1);

        this.images = new ArrayList<>();
        this.productVariantDTOS = new ArrayList<>();
        this.categoriesDTOS = new ArrayList<>();

        if (product.getImages() != null) {
            for (ProductImage img : product.getImages()) {
                if (img != null && img.getUrlImage() != null) {
                    if (img.getIsMain()) {
                        this.mainImage = img.getUrlImage();
                        continue;
                    }
                    this.images.add(img.getUrlImage());
                }
            }
        }

        if (product.getVariants() != null) {
            for (ProductVariant variant : product.getVariants()) {
                if (variant != null) {
                    String variantImgUrl = null;
                    if (variant.getProductImage() != null) {
                        variantImgUrl = variant.getProductImage().getUrlImage();
                    }

                    ProductVariantDTO variantDTO = new ProductVariantDTO(
                            variant.getId(),
                            variant.getSku(),
                            variant.getName(),
                            variant.getPrice(),
                            variant.getStock(),
                            variant.getColor(),
                            variant.getSize(),
                            variant.getGram(),
                            variantImgUrl
                    );

                    this.productVariantDTOS.add(variantDTO);
                }
            }
        }

        if (product.getProductCategories() != null) {
            for (ProductCategory pc : product.getProductCategories()) {
                if (pc != null && pc.getCategory() != null) {
                    CategoriesDTO catDTO = new CategoriesDTO();
                    catDTO.setId(product.getCategory().getId());
                    catDTO.setCategoriesName(product.getCategory().getCategoryName());
                    this.categoriesDTOS.add(catDTO);
                }
            }
        }
    }
}
