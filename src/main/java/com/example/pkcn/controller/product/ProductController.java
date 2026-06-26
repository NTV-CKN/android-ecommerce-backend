package com.example.pkcn.controller.product;

import com.example.pkcn.dto.response.FeatureProductDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.dto.response.ProductDetailsDTO;
import com.example.pkcn.service.product.IProductService;
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
    public ResponseEntity<List<FeatureProductDTO>> getFeatureProduct(@RequestParam(required = false) Integer categoryId,
                                                                     @RequestParam(defaultValue = "8") int limit) {
        List<FeatureProductDTO> list = productService.getFeatureProduct(categoryId, limit);
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

    @GetMapping("/category")
    public ResponseEntity<PageResponseDTO<FeatureProductDTO>> getProductByCategory(
            @RequestParam(required = false) Integer categoryId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String direction

    ) {
        return ResponseEntity.ok(
                productService.getProductByCategory(
                        categoryId,
                        page,
                        pageSize,
                        minPrice,
                        maxPrice,
                        sortBy,
                        keyword,
                        direction
                )
        );
    }

}
