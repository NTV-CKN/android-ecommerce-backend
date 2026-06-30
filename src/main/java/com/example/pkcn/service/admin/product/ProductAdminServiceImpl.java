package com.example.pkcn.service.admin.product;

import com.example.pkcn.dto.ProductAdminPageDTO;
import com.example.pkcn.dto.response.CategoriesDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.dto.response.ProductVariantDTO;
import com.example.pkcn.dto.response.SuccessBasicDTO;
import com.example.pkcn.entity.*;
import com.example.pkcn.repository.admin.product.IProductAdminRepository;
import com.example.pkcn.repository.categories.ICategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductAdminServiceImpl implements IProductAdminService {
    private IProductAdminRepository productAdminRepository;
    private ICategoriesRepository categoriesRepository;

    @Autowired
    public ProductAdminServiceImpl(
            IProductAdminRepository productAdminRepository,
            ICategoriesRepository categoriesRepository
    ) {
        this.categoriesRepository = categoriesRepository;
        this.productAdminRepository = productAdminRepository;
    }

    @Override
    @Transactional
    public PageResponseDTO<ProductAdminPageDTO> getProducts(String keyWord, Integer page, Integer pageSize) {
        if (keyWord != null && keyWord.trim().isEmpty()) {
            keyWord = null;
        }


        long totalElement = productAdminRepository.getTotalElementByKeywordAndNameCategory(
                keyWord);

        List<Product> products = productAdminRepository.getProducts(
                keyWord, page, pageSize);

        List<ProductAdminPageDTO> productsAdmin = new ArrayList<>();

        for (Product product : products) {
            ProductAdminPageDTO productAdminPageDTO = new ProductAdminPageDTO();
            productAdminPageDTO.initData(product);
            productsAdmin.add(productAdminPageDTO);
        }

        PageResponseDTO<ProductAdminPageDTO> response = new PageResponseDTO<>(
                productsAdmin,
                page,
                pageSize,
                totalElement
        );

        return response;
    }

    @Override
    public String generateUniqueSku(String productName, String color, String size) {
        String pCode = convertToRawCode(productName);
        String cCode = convertToRawCode(color);
        String sCode = convertToRawCode(size);

        String baseSku = String.format("%s-%s-%s", pCode, cCode, sCode);
        String finalSku = baseSku;

        boolean isDuplicate = productAdminRepository.existsBySku(finalSku);
        int safetyCounter = 0;

        while (isDuplicate && safetyCounter < 100) {
            int randomTail = (int) (Math.random() * 900) + 100;
            finalSku = baseSku + "-" + randomTail;
            isDuplicate = productAdminRepository.existsBySku(finalSku);
            safetyCounter++;
        }

        return finalSku;
    }

    @Transactional
    @Override
    public SuccessBasicDTO saveProduct(ProductAdminPageDTO dto) {
        Product product = new Product();
        product.setCreateDate(LocalDateTime.now());
        product.setUpdateDate(LocalDateTime.now());
        product.setFeatured(false);

        product.setFolderId(dto.getFolderId());
        product.setName(dto.getName());
        product.setSubtitle(dto.getSubtitle());
        product.setDescription(dto.getDescription());
        product.setWarrantyPeriod(dto.getWarrantyPeriod());
        product.setStatus(1);

        int totalStock = 0;
        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;

        if (dto.getProductVariantDTOS() != null) {
            for (ProductVariantDTO vDto : dto.getProductVariantDTOS()) {
                totalStock += (vDto.getStock() != null) ? vDto.getStock() : 0;
                BigDecimal price = vDto.getPrice();
                if (price != null) {
                    if (minPrice == null || price.compareTo(minPrice) < 0) minPrice = price;
                    if (maxPrice == null || price.compareTo(maxPrice) > 0) maxPrice = price;
                }
            }
        }

        product.setStock(totalStock);
        product.setMinPrice(minPrice != null ? minPrice : BigDecimal.ZERO);
        product.setMaxPrice(maxPrice != null ? maxPrice : BigDecimal.ZERO);

        if(Objects.equals(product.getMinPrice(), product.getMaxPrice()))
            product.setMaxPrice(null);

        final Product savedProduct = productAdminRepository.saveProduct(product);

        List<ProductImage> productImages = new ArrayList<>();

        if (dto.getMainImage() != null && !dto.getMainImage().trim().isEmpty()) {
            ProductImage mainImg = new ProductImage();
            mainImg.setUrlImage(dto.getMainImage());
            mainImg.setIsMain(true);
            mainImg.setProduct(savedProduct);
            productImages.add(mainImg);
        }

        if (dto.getImages() != null) {
            for (String subImageUrl : dto.getImages()) {
                if (subImageUrl != null && !subImageUrl.trim().isEmpty()) {
                    ProductImage subImg = new ProductImage();
                    subImg.setUrlImage(subImageUrl);
                    subImg.setIsMain(false);
                    subImg.setProduct(savedProduct);
                    productImages.add(subImg);
                }
            }
        }

        if (dto.getProductVariantDTOS() != null) {
            for (ProductVariantDTO vDto : dto.getProductVariantDTOS()) {
                ProductVariant variant = new ProductVariant();
                variant.setSku(vDto.getSku());
                variant.setName(vDto.getName());
                variant.setPrice(vDto.getPrice());
                variant.setStock(vDto.getStock());
                variant.setColor(vDto.getColor());
                variant.setSize(vDto.getSize());
                variant.setGram(vDto.getGram());
                variant.setProduct(savedProduct);
                variant.setCreateDate(LocalDateTime.now());
                variant.setUpdateDate(LocalDateTime.now());

                ProductVariant savedVariant = productAdminRepository.saveProductVariant(variant);

                if (vDto.getImageUrl() != null && !vDto.getImageUrl().trim().isEmpty()) {
                    ProductImage variantImg = new ProductImage();
                    variantImg.setUrlImage(vDto.getImageUrl());
                    variantImg.setIsMain(false);
                    variantImg.setProduct(savedProduct);
                    variantImg.setProductVariant(savedVariant);
                    productImages.add(variantImg);
                }
            }
        }

        if (!productImages.isEmpty()) {
            productAdminRepository.saveProductImages(productImages);
        }

        if (dto.getCategoriesDTOS() != null) {
            for (CategoriesDTO catDto : dto.getCategoriesDTOS()) {
                Categories category = categoriesRepository.findById(catDto.getId());

                ProductCategory productCategory = new ProductCategory(savedProduct, category);
                productAdminRepository.saveProductCategory(productCategory);
            }
        }

        return new SuccessBasicDTO(
                "Lưu thành công",
                true
        );
    }

    private String convertToRawCode(String input) {
        if (input == null || input.trim().isEmpty()) return "X";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^a-zA-Z0-9]", "")
                .toUpperCase();
        return normalized.isEmpty() ? "X" : normalized;
    }
}
