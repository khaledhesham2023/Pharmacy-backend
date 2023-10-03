package com.khaledamin.pharmacy.cart;

import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CartRepo extends JpaRepository<CartEntity, Long> {

    List<CartEntity> findByUser(UserEntity user);

    CartEntity findByUserAndProduct(UserEntity user, ProductEntity product);

    void deleteByUser(UserEntity user);

}