package com.khaledamin.pharmacy.model.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCartResponse {
    private boolean status;
    private String message;
    private long cartId;
}
