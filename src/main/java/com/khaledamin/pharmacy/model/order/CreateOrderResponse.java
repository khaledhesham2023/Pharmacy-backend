package com.khaledamin.pharmacy.model.order;

import com.khaledamin.pharmacy.base.BaseResponse;
import com.khaledamin.pharmacy.order.OrderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateOrderResponse {
    private BaseResponse response;
    private Order order;
}
