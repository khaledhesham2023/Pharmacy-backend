package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.catalog.Category;
import com.khaledamin.pharmacy.model.catalog.GetCatalogResponse;
import com.khaledamin.pharmacy.model.catalog.Slider;
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


    public GetCatalogResponse getCatalog(String lang){
        List<SliderEntity> sliders = sliderRepo.findAll();
        List<CategoryEntity> categories = categoryRepo.findAll();
        List<Slider> sliderList = new ArrayList<>();
        List<Category> categoryList = new ArrayList<>();
        for (SliderEntity slider:
             sliders) {
            switch (lang) {
                case "ar" -> {
                    Slider slider1 = Slider.builder()
                            .id(slider.getSliderId())
                            .sliderTitle(slider.getSliderTitle())
                            .sliderImage(slider.getSliderImage())
                            .build();
                    sliderList.add(slider1);
                }
                case "en" -> {
                    Slider slider2 = Slider.builder()
                            .id(slider.getSliderId())
                            .sliderTitle(slider.getSliderTitleAr())
                            .sliderImage(slider.getSliderImage())
                            .build();
                    sliderList.add(slider2);
                }
            }
        }
        for (CategoryEntity category:
                categories) {
            switch (lang) {
                case "ar" -> {
                    Category category1 = Category.builder()
                            .id(category.getCategoryId())
                            .categoryTitle(category.getCategoryName())
                            .categoryImage(category.getCategoryImage())
                            .build();
                    categoryList.add(category1);
                }
                case "en" -> {
                    Category category2 = Category.builder()
                            .id(category.getCategoryId())
                            .categoryTitle(category.getCategoryNameAr())
                            .categoryImage(category.getCategoryImage())
                            .build();
                    categoryList.add(category2);
                }
            }
        }
        return GetCatalogResponse.builder()
                .sliders(sliderList)
                .categories(categoryList)
                .build();
    }
}
