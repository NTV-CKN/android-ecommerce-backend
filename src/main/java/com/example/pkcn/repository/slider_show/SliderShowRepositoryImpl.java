package com.example.pkcn.repository.slider_show;

import com.example.pkcn.entity.SliderShow;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SliderShowRepositoryImpl implements ISliderShowRepository {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<SliderShow> getSliderShow() {
        String query = "SELECT s FROM SliderShow s";
        return em.createQuery(query, SliderShow.class).getResultList();
    }
}
