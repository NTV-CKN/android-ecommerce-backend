package com.example.pkcn.repository.categories;

import com.example.pkcn.dto.response.CategoriesDTO;
import com.example.pkcn.entity.Categories;

import java.util.List;

public interface ICategoriesRepository {
    List<Categories> findByParentCategoriesIsNull();
    Categories findById(Integer id);
}
