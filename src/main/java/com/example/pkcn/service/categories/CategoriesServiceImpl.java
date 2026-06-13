package com.example.pkcn.service.categories;

import com.example.pkcn.dto.response.CategoriesDTO;
import com.example.pkcn.entity.Categories;
import com.example.pkcn.repository.categories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements ICategoriesService {

    @Autowired
    CategoriesRepository categoriesRepository;

    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<CategoriesDTO> getParentCategories() {
        List<Categories> categories = categoriesRepository.findByParentCategoriesIsNull();
        return categories.stream().map(c -> {
            CategoriesDTO categoriesDTO = new CategoriesDTO();
            categoriesDTO.setId(c.getId());
            categoriesDTO.setCategoriesName(c.getCategoryName());
            return categoriesDTO;
        }).toList();
    }
}
