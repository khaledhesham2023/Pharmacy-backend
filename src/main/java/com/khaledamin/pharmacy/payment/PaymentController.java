package com.khaledamin.pharmacy.payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/V1/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    @PreAuthorize("hasAuthority('ORDERS')")
    public ResponseEntity<List<PaymentEntity>> getPaymentMethods(){
        return ResponseEntity.ok(paymentService.getPaymentMethods());
    }
}
