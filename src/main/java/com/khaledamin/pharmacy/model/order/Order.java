package com.khaledamin.pharmacy.model.order;

import com.khaledamin.pharmacy.address.AddressEntity;
import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.model.cart.CartProductItem;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.order.OrderItemsEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {

    private long id;
    private String dateCreated;
    private double subtotal;
    private double shipping;
    private double total;
    private AddressEntity address;
    private double discount;
    private String email;
    private String firstname;
    private String lastname;
    private String incrementId;
    private List<OrderItemsEntity> products;
    private String payment;
    private String status;
}
