package com.example.pkcn.dto.response;

public class PaymentMethodDTO {
    private Integer id;
    private String nameMethod;
    private String subtitle;

    public PaymentMethodDTO() {
    }

    public PaymentMethodDTO(Integer id, String nameMethod, String subtitle) {
        this.id = id;
        this.nameMethod = nameMethod;
        this.subtitle = subtitle;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNameMethod() { return nameMethod; }
    public void setNameMethod(String nameMethod) { this.nameMethod = nameMethod; }

    public String getSubtitle() { return subtitle; }
    public void setSubtitle(String subtitle) { this.subtitle = subtitle; }
}