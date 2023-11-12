package com.khaledamin.pharmacy.model.catalog;

import com.khaledamin.pharmacy.main.CategoryEntity;
import com.khaledamin.pharmacy.main.SliderEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.A;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetCatalogResponse {
    private List<SliderEntity> sliders;
    private List<CategoryEntity> categories;

}
