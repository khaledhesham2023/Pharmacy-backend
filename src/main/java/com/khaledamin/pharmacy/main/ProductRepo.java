package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity,Long> {

//    @Query(value = "SELECT * FROM products where subcategory_id = :id",nativeQuery = true)
//    List<ProductEntity> getProductsBySubCategoryId(@Param("id") long subcategoryId);
    List<ProductEntity> findBySubcategory(SubCategoryEntity subCategoryEntity);
}
