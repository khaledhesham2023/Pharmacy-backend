package com.khaledamin.pharmacy.model.catalog;

import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.model.category.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Slider {
    private Long id;
    private String sliderTitleAr;
    private String sliderImage;
    private ProductItem product;
}
