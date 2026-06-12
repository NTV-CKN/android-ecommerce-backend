package com.example.pkcn.controller.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.service.product.IProductService;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<List<FeatureProductDTO>> getFeatureProduct(Pageable pageable) {
        List<FeatureProductDTO> list = productService.getFeatureProduct(pageable);
        return ResponseEntity.ok(list);
    }
}
