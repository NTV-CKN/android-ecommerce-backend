package com.example.pkcn.controller.ai;

import com.example.pkcn.dto.response.ai.ProductChatSummaryDTO;
import com.example.pkcn.dto.response.ai.client.BotChatResponseDTO;

import com.example.pkcn.utils.TempChatContext;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/ai")
public class ChatAiController {
    private final ChatModel chatModel;

    @Autowired
    public ChatAiController(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @PostMapping("/chat")
    public BotChatResponseDTO handleUserChat(@RequestParam String userMessage) {
        String systemInstruction = "Bạn là trợ lý ảo tư vấn bán hàng thông minh của shop Phụ Kiện Công Nghệ. " +
                "Hãy trả lời thân thiện, ngắn gọn. Nếu khách hàng muốn tìm sản phẩm hoặc mua hàng, bạn BẮT BUỘC phải " +
                "gọi công cụ 'productSearchFunction' để lấy dữ liệu thực tế và tư vấn dựa trên danh sách đó. " +
                "QUAN TRỌNG: Khi gọi công cụ, CHỈ điền các tham số mà khách hàng đã thực sự đề cập trong câu hỏi. " +
                "TUYỆT ĐỐI KHÔNG tự suy đoán, không đặt giá trị mặc định, không bịa ra giá tiền hay màu sắc " +
                "nếu khách hàng không nói rõ. Nếu khách hàng không đề cập giá hoặc màu, hãy để trống các trường đó. " +
                "Trong trường hợp khách có hỏi kèm hoặc hỏi về thông tin của cửa hàng, bạn PHẢI gọi công cụ 'getShopInfoFunction' " +
                "dựa vào kết quả để trả lời những gì mà họ muốn và không được bịa ra thông tin không có.";

        Set<String> functions = new HashSet<>();
        functions.add("productSearchFunction");
        functions.add("getShopInfoFunction");

        GoogleGenAiChatOptions options = GoogleGenAiChatOptions.builder()
                .toolNames(functions)
                .build();

        String fullPrompt = systemInstruction + "\n\nCâu hỏi của khách hàng: " + userMessage;
        Prompt prompt = new Prompt(fullPrompt, options);

        ChatResponse chatResponse = chatModel.call(prompt);

        String botReplyText = chatResponse.getResult().getOutput().getText();

        List<ProductChatSummaryDTO> finalProducts = new ArrayList<>();

        try {
            List<ProductChatSummaryDTO> savedList = TempChatContext.getProducts();
            if (savedList != null && !savedList.isEmpty()) {
                finalProducts.addAll(savedList);
            }
        } finally {
            TempChatContext.clear();
        }

        boolean hasProducts = !finalProducts.isEmpty();
        return new BotChatResponseDTO(botReplyText, hasProducts, finalProducts);
    }
}