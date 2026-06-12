package com.example.pkcn.repository.categories;

import com.example.pkcn.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
    List<Categories> findByParentCategoriesIsNull();
}
