package com.example.pkcn.utils;

import com.example.pkcn.dto.response.VoucherDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;

public class VoucherJsonUtil {

    private static final ObjectMapper mapper =
            new ObjectMapper()
                    .registerModule(
                            new JavaTimeModule()
                    );

    /*
     * Convert List<VoucherDTO> -> JSON String
     */
    public static String toJson(
            List<VoucherDTO> vouchers
    ) {

        try {

            if (vouchers == null) {
                return "[]";
            }

            return mapper.writeValueAsString(
                    vouchers
            );

        } catch (JsonProcessingException e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }

    /*
     * Convert JSON -> List<VoucherDTO>
     */
    public static List<VoucherDTO> fromJson(
            String json
    ) {

        try {

            if (json == null ||
                    json.isEmpty()) {

                return new ArrayList<>();
            }

            return mapper.readValue(
                    json,
                    new TypeReference<
                            List<VoucherDTO>>() {}
            );

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage()
            );
        }
    }
}