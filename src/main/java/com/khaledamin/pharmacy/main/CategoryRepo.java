package com.khaledamin.pharmacy.main;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<CategoryEntity,Long> {

    @Query(value = "SELECT * FROM categories c INNER JOIN subcategories s ON c.category_id = s.category_id INNER JOIN products p ON s.subcategory_id = p.subcategory_id WHERE p.product_id = :id", nativeQuery = true)
    CategoryEntity getCategoryByProduct(@Param("id") long productId);

}
