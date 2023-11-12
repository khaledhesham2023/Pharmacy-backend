package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.catalog.Category;
import com.khaledamin.pharmacy.model.catalog.GetCatalogResponse;
import com.khaledamin.pharmacy.model.catalog.Slider;
import com.khaledamin.pharmacy.model.category.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CatalogService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private SliderRepo sliderRepo;

    @Autowired
    private ProductRepo productRepo;

    public GetCatalogResponse getCatalog(){
        List<SliderEntity> sliders = sliderRepo.findAll();
        List<CategoryEntity> categories = categoryRepo.findAll();
        return GetCatalogResponse.builder()
                .sliders(sliders)
                .categories(categories)
                .build();
    }
}
