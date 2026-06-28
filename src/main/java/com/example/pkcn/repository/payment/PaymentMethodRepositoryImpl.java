package com.example.pkcn.repository.payment;

import com.example.pkcn.dto.response.PaymentMethodDTO;
import com.example.pkcn.entity.PaymentMethod;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PaymentMethodRepositoryImpl implements IPaymentMethodRepository{
    private EntityManager em;
    public PaymentMethodRepositoryImpl(EntityManager em) {
        this.em = em;
    }
    @Override
    public List<PaymentMethod> getPaymentMethods() {
        String sql = "SELECT p FROM PaymentMethod p";
        TypedQuery<PaymentMethod> query = em.createQuery(sql, PaymentMethod.class);
        return query.getResultList();
    }
}
