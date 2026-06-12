package com.example.pkcn.service.categories;

import com.example.pkcn.dto.response.CategoriesDTO;

import java.util.List;

public interface ICategoriesService {
    public List<CategoriesDTO> getParentCategories();
}
