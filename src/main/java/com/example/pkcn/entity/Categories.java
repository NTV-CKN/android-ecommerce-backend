package com.example.pkcn.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "categories")
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Categories parentCategories;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "parentCategories")
    private List<Categories> childrenCategories;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Categories getParentCategories() {
        return parentCategories;
    }

    public void setParentCategories(Categories parentCategories) {
        this.parentCategories = parentCategories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Categories> getChildrenCategories() {
        return childrenCategories;
    }

    public void setChildrenCategories(List<Categories> childrenCategories) {
        this.childrenCategories = childrenCategories;
    }
}
