package com.example.pkcn.controller.admin;

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
    public PageResponseDTO<Product> getProducts(
            @RequestParam(name = "key_word") String keyWord,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "12", name = "page_size") Integer pageSize,
            @RequestParam(name = "name_category") String nameCategory
    ) {

        return null;
    }
}
