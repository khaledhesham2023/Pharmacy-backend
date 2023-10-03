package com.khaledamin.pharmacy.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStatusRepo extends JpaRepository<OrderStatusEntity,Long> {

    OrderStatusEntity findByStatusName(String status);
}
