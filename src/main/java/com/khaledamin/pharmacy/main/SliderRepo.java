package com.khaledamin.pharmacy.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SliderRepo extends JpaRepository<SliderEntity,Long> {
}
