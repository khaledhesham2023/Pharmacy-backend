package com.khaledamin.pharmacy.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubCategoryRepo extends JpaRepository<SubCategoryEntity,Long> {

//    @Query(value = "SELECT * FROM subcategories where category_id = :id",nativeQuery = true)
//    List<SubCategoryEntity> findAllSubCategories(@Param("id") long categoryId);

    List<SubCategoryEntity> findByCategory(CategoryEntity category);
}
