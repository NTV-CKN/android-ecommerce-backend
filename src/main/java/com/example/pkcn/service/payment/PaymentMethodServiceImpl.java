package com.example.pkcn.service.payment;

import com.example.pkcn.dto.response.PaymentMethodDTO;
import com.example.pkcn.entity.PaymentMethod;
import com.example.pkcn.repository.payment.IPaymentMethodRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class PaymentMethodServiceImpl implements IPaymentMethodService {
    private final IPaymentMethodRepository paymentMethodRepository;
    public PaymentMethodServiceImpl(IPaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }
    @Override
    public List<PaymentMethodDTO> getPaymentMethods() {
        List<PaymentMethod> entity = paymentMethodRepository.getPaymentMethods();
        List<PaymentMethodDTO> list = new ArrayList<>();
        for (PaymentMethod e : entity) {
            PaymentMethodDTO dto = new PaymentMethodDTO(
                    e.getId(),
                    e.getNameMethod(),
                    e.getSubtitle()
            );
            list.add(dto);
        }
        return list;
    }
}
