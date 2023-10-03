package com.khaledamin.pharmacy.model.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateCartRequest {
    private long userId;
    private long productId;
    private int quantity;

}
