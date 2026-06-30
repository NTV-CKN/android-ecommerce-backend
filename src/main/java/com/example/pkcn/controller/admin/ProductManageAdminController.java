package com.example.pkcn.controller.admin;

import com.example.pkcn.dto.ProductAdminPageDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.entity.Product;
import com.example.pkcn.service.admin.product.IProductAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api/v1/admin-product")
@RestController
public class ProductManageAdminController {
    private IProductAdminService productAdminService;

    @Autowired
    public ProductManageAdminController(
            IProductAdminService productAdminService
    ) {
        this.productAdminService = productAdminService;
    }

    @GetMapping("/products")
    public PageResponseDTO<ProductAdminPageDTO> getProducts(
            @RequestParam("keyWord") String keyWord,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "12") Integer pageSize) {

        return productAdminService.getProducts(
                keyWord, page, pageSize
        );
    }

    @GetMapping("/generate-sku")
    public ResponseEntity<Map<String, String>> generateAndCheckSku(
            @RequestParam String productName,
            @RequestParam String color,
            @RequestParam String size) {

        String uniqueSku = productAdminService.generateUniqueSku(productName, color, size);

        Map<String, String> response = new HashMap<>();
        response.put("sku", uniqueSku);
        return ResponseEntity.ok(response);
    }
}
