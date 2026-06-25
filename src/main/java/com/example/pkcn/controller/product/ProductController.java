package com.example.pkcn.controller.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.service.product.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/feature")
    public ResponseEntity<List<FeatureProductDTO>> getFeatureProduct(int limit) {
        List<FeatureProductDTO> list = productService.getFeatureProduct(limit);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FeatureProductDTO>> searchProduct(@RequestParam String keyword) {
        keyword = keyword.trim();
        return ResponseEntity.ok(
                productService.searchProduct(keyword)
        );
    }


}
