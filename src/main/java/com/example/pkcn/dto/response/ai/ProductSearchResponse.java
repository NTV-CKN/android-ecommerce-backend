package com.example.pkcn.dto.response.ai;

import java.util.List;

public record ProductSearchResponse(
        List<ProductChatSummaryDTO> products
) {}