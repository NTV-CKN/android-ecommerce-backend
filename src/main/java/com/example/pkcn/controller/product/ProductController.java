package com.example.pkcn.controller.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.dto.response.ProductDetailsDTO;
import com.example.pkcn.service.product.IProductService;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public ResponseEntity<ProductDetailsDTO> getProductDetails(@PathVariable ("id") int id) {
        ProductDetailsDTO detailsDTO = productService.getProductDetails(id);
        return ResponseEntity.ok(detailsDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<List<FeatureProductDTO>> searchProduct(@RequestParam String keyword) {
        keyword = keyword.trim();
        return ResponseEntity.ok(
                productService.searchProduct(keyword)
        );
    }


}
