package com.khaledamin.pharmacy.model.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateOrderRequest {
    private long userId;
    private long shippingId;
    private long paymentId;


}
