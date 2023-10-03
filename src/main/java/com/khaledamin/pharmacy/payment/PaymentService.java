package com.khaledamin.pharmacy.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepo paymentRepo;

    public List<PaymentEntity> getPaymentMethods(){
        return paymentRepo.findAll();
    }
}
