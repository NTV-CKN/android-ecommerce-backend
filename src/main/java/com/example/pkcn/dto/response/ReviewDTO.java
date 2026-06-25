package com.example.pkcn.dto.response;

public class ReviewDTO {
    private Integer id;
    private String name;
    private Integer numStar;
    private String evaluate;;
    private String evaluateDate;

    public ReviewDTO() {
    }

    public ReviewDTO(Integer id, String name, Integer numStar, String evaluate, String evaluateDate) {
        this.id = id;
        this.name = name;
        this.numStar = numStar;
        this.evaluate = evaluate;
        this.evaluateDate = evaluateDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumStar() {
        return numStar;
    }

    public void setNumStar(Integer numStar) {
        this.numStar = numStar;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public String getEvaluateDate() {
        return evaluateDate;
    }

    public void setEvaluateDate(String evaluateDate) {
        this.evaluateDate = evaluateDate;
    }
}
