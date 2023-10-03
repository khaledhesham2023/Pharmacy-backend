package com.khaledamin.pharmacy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/V2/")
public class HelloController {

    @PostMapping("hello")
    @PreAuthorize("hasAuthority('DISCOUNTS')")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World !");
    }

}
