package com.example.pkcn.dto.response;

public class VoucherTypeDTO {
    private Integer id;
    private String code;
    private String name;

    public VoucherTypeDTO(Integer id, String code, String name) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public Integer getId()   { return id; }
    public String getCode()  { return code; }
    public String getName()  { return name; }
}
