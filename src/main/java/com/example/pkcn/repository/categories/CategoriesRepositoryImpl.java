package com.example.pkcn.repository.categories;

import com.example.pkcn.dto.response.CategoriesDTO;
import com.example.pkcn.entity.Categories;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoriesRepositoryImpl implements ICategoriesRepository {

    private EntityManager em;

    public CategoriesRepositoryImpl(EntityManager em) {
        this.em = em;
    }

    @Override
    public List<Categories> findByParentCategoriesIsNull() {
        String sql = "SELECT c FROM Categories c WHERE c.parentCategories IS NULL";
        TypedQuery<Categories> query = em.createQuery(sql, Categories.class);
        return query.getResultList();
    }
}
