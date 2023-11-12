package com.khaledamin.pharmacy.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReorderRequest {
    private long orderId;
    private long userId;
}
