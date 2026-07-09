package com.example.pkcn.dto.request.ai;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.math.BigDecimal;

public record ProductSearchRequest(
        @JsonPropertyDescription("Từ khóa tên sản phẩm người dùng muốn tìm kiếm, ví dụ: ốp lưng, tai nghe, sạc dự phòng, dây cáp tín hiệu, bàn phím, tấm che ô tô")
        String keyword,
        @JsonPropertyDescription("Mức giá tối đa người dùng có thể chi trả cho sản phẩm")
        BigDecimal maxPrice,
        @JsonPropertyDescription("Màu sắc của sản phẩm nếu người dùng có chỉ định rõ ràng")
        String color
) {}