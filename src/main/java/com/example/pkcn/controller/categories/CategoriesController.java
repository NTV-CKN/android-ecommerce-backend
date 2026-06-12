package com.example.pkcn.controller.categories;

import com.example.pkcn.dto.response.CategoriesDTO;
import com.example.pkcn.service.categories.ICategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoriesController {
    @Autowired
    private ICategoriesService categoriesService;

    @GetMapping("/parents")
    public ResponseEntity<List<CategoriesDTO>> getParentCategories() {
        List<CategoriesDTO> parents = categoriesService.getParentCategories();
        return ResponseEntity.ok(parents);
    }
}
