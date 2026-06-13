package com.example.pkcn.service.categories;

import com.example.pkcn.dto.response.CategoriesDTO;
import com.example.pkcn.entity.Categories;
import com.example.pkcn.repository.categories.ICategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesServiceImpl implements ICategoriesService {

    private final ICategoriesRepository categoriesRepository;

    @Autowired
    public CategoriesServiceImpl(ICategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<CategoriesDTO> getParentCategories() {
        List<Categories> categories = categoriesRepository.findByParentCategoriesIsNull();
        return categories.stream().map(c -> {
            CategoriesDTO dto = new CategoriesDTO();
            dto.setId(c.getId());
            dto.setCategoriesName(c.getCategoryName());
            return dto;
        }).toList();
    }
}
