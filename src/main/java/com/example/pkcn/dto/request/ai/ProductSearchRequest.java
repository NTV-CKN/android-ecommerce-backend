package com.example.pkcn.dto.request.ai;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

public record ProductSearchRequest(
        @JsonPropertyDescription(
                "Từ khóa liên quan đến sản phẩm hoặc danh mục sản phẩm mà khách hàng đang quan tâm. " +
                        "Có thể là tên sản phẩm cụ thể, loại phụ kiện, hoặc mô tả chung chung. " +
                        "Ví dụ: ốp lưng, tai nghe, sạc dự phòng, dây cáp, bàn phím, chuột, phụ kiện ô tô. " +
                        "Nếu khách hàng chỉ chào hỏi hoặc chưa đề cập sản phẩm cụ thể, để trống hoặc null."
        )
        @Nullable
        String keyword,

        @JsonPropertyDescription(
                "Mức giá tối đa (VNĐ) mà khách hàng sẵn sàng chi trả, CHỈ điền nếu khách hàng có đề cập cụ thể đến ngân sách/giá tiền. " +
                        "Không tự suy đoán hoặc đặt giá trị mặc định nếu khách hàng không nói gì về giá."
        )
        @Nullable
        BigDecimal maxPrice,

        @JsonPropertyDescription(
                "Màu sắc cụ thể mà khách hàng yêu cầu, CHỈ điền nếu khách hàng có nhắc rõ ràng đến màu sắc. " +
                        "Để trống nếu không được đề cập."
        )
        @Nullable
        String color
) {}