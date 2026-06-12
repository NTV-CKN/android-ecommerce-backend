package com.example.pkcn.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String email;
    private String name;
    private String evaluate;

    @Column(name = "num_star")
    private Integer numStar;

    @Column(name = "evaluate_date")
    private LocalDateTime evaluateDate;

    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public Integer getNumStar() {
        return numStar;
    }

    public void setNumStar(Integer numStar) {
        this.numStar = numStar;
    }

    public LocalDateTime getEvaluateDate() {
        return evaluateDate;
    }

    public void setEvaluateDate(LocalDateTime evaluateDate) {
        this.evaluateDate = evaluateDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
