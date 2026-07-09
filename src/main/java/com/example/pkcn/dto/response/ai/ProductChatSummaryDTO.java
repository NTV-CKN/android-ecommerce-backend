package com.example.pkcn.dto.response.ai;

import java.math.BigDecimal;

public record ProductChatSummaryDTO(
        Integer id,
        String name,
        BigDecimal minPrice,
        String mainImageUrl,
        Integer stock
) {}
