package com.example.pkcn.configuration;

import com.example.pkcn.dto.request.ai.ProductSearchRequest;
import com.example.pkcn.dto.response.ai.ProductSearchResponse;
import com.example.pkcn.service.ai.ProductSearchTool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class AiConfig {

    @Bean
    @Description("Tìm kiếm danh sách các sản phẩm phụ kiện công nghệ trong kho dựa theo từ khóa, mức giá tối đa, hoặc màu sắc khi người dùng có nhu cầu tìm sản phẩm hoặc mua hàng.")
    public Function<ProductSearchRequest, ProductSearchResponse> productSearchFunction(ProductSearchTool searchTool) {
        return searchTool;
    }
}