package com.khaledamin.pharmacy.order;

import com.khaledamin.pharmacy.cart.CartEntity;
import com.khaledamin.pharmacy.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemsRepo extends JpaRepository<OrderItemsEntity,Long> {

    List<OrderItemsEntity> findByUserAndOrder(UserEntity user,OrderEntity order);
}
