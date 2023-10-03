package com.khaledamin.pharmacy.shipping;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/V1/shipping")
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    @GetMapping
    @PreAuthorize("hasAuthority('ORDERS')")
    public ResponseEntity<List<ShippingEntity>> getShippingMethods(){
        return ResponseEntity.ok(shippingService.getShippingMethods());
    }
}
