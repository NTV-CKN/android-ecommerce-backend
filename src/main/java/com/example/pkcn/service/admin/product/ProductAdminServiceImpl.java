package com.example.pkcn.service.admin.product;

import com.example.pkcn.dto.response.PageResponseDTO;
import com.example.pkcn.entity.Product;
import com.example.pkcn.repository.admin.product.IProductAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public PageResponseDTO<Product> getProducts(String keyWord, Integer page, Integer pageSize, String nameCategory) {
        return null;
    }
}
