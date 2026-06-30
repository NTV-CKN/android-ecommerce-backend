package com.example.pkcn.dto.response;

public class VoucherTypeDTO {

    private Integer id;
    private String code;
    private String name;

    // BẮT BUỘC
    public VoucherTypeDTO() {
    }

    public VoucherTypeDTO(
            Integer id,
            String code,
            String name
    ) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Integer getId() { return id; }

    public String getCode() { return code; }

    public String getName() { return name; }

    // setter
    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }
}