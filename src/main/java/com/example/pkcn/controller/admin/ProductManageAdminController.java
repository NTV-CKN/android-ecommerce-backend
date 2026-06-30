package com.example.pkcn.controller.admin;

import com.example.pkcn.service.admin.product.IProductAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
