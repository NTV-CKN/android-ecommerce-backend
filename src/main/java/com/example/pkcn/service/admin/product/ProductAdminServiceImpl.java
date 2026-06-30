package com.example.pkcn.service.admin.product;

import com.example.pkcn.dto.ProductAdminPageDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.entity.Product;
import com.example.pkcn.repository.admin.product.IProductAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductAdminServiceImpl implements IProductAdminService{
    private IProductAdminRepository productAdminRepository;

    @Autowired
    public ProductAdminServiceImpl(
            IProductAdminRepository productAdminRepository
    ) {
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

        for(Product product: products) {
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

    private String convertToRawCode(String input) {
        if (input == null || input.trim().isEmpty()) return "X";
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^a-zA-Z0-9]", "")
                .toUpperCase();
        return normalized.isEmpty() ? "X" : normalized;
    }
}
