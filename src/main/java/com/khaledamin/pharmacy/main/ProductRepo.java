package com.khaledamin.pharmacy.main;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<ProductEntity, Long> {

    //    @Query(value = "SELECT * FROM products where subcategory_id = :id",nativeQuery = true)
//    List<ProductEntity> getProductsBySubCategoryId(@Param("id") long subcategoryId);
    Page<ProductEntity> findBySubcategory(SubCategoryEntity subCategoryEntity, Pageable pageable);

    List<ProductEntity> findByProductNameContainingIgnoreCase(String query);

    @Query(value = "SELECT * FROM products p INNER JOIN subcategories s ON p.subcategory_id = s.subcategory_id INNER JOIN categories c on s.category_id = c.category_id WHERE c.category_id = :id", nativeQuery = true)
    Page<ProductEntity> getProductsByCategory(@Param("id") long categoryId, Pageable pageable);

    @Query(value = "SELECT * FROM products p INNER JOIN subcategories s ON p.subcategory_id = s.subcategory_id INNER JOIN categories c on s.category_id = c.category_id WHERE c.category_id = :id AND p.product_id != :productId ORDER BY RAND()", nativeQuery = true)
    Page<ProductEntity> getProductsByCategoryNotContainingCurrent(@Param("id") long categoryId, Pageable pageable, @Param("productId") long productId);

    @Query(value = "select subcategory_id from products where product_id = :id",nativeQuery = true)
    Long getSubCategoryId(@Param("id") long productId);

}
