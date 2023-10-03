package com.khaledamin.pharmacy.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingService {

    @Autowired
    private ShippingRepo shippingRepo;

    public List<ShippingEntity> getShippingMethods(){
        return shippingRepo.findAll();
    }
}
