package com.example.pkcn.controller.admin;

import com.example.pkcn.dto.ProductAdminPageDTO;
import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.entity.Product;
import com.example.pkcn.service.admin.product.IProductAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
