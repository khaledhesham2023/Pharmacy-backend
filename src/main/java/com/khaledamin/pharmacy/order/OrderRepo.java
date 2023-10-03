package com.khaledamin.pharmacy.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<OrderEntity,Long> {

    @Query(value = "SELECT * FROM ORDERS WHERE status_id = 1 OR status_id = 2 OR status_id = 3 AND id = :id",nativeQuery = true)
    List<OrderEntity> getCurrentOrders(@Param("id") long id);

    @Query(value = "SELECT * FROM ORDERS WHERE status_id = 4 OR status_id = 5 OR status_id = 6 AND id = :id",nativeQuery = true)
    List<OrderEntity> getPreviousOrders(@Param("id") long id);
}
