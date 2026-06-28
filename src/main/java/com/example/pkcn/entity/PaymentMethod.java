package com.example.pkcn.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_method")
    private String nameMethod;

    private String subtitle;

    public PaymentMethod() {
    }

    public PaymentMethod(String nameMethod, String subtitle) {
        this.nameMethod = nameMethod;
        this.subtitle = subtitle;
    }

    public Integer getId() {
        return id;
    }

    public String getNameMethod() {
        return nameMethod;
    }

    public void setNameMethod(String nameMethod) {
        this.nameMethod = nameMethod;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}