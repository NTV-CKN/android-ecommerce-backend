package com.example.pkcn.dto.response.ai.client;

import com.example.pkcn.dto.response.ai.ProductChatSummaryDTO;

import java.util.List;

public record BotChatResponseDTO(
        String botReply,
        boolean hasProducts,
        List<ProductChatSummaryDTO> suggestedProducts
) {}