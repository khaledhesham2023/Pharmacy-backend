package com.khaledamin.pharmacy.main;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CatalogServiceTest {

    @Autowired
    private SliderRepo sliderRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private SubCategoryRepo subCategoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Test
    public void addSlider() {
        CategoryEntity category = categoryRepo.findById(2L).orElseThrow();
        ProductEntity product =
                ProductEntity.builder()
                        .productName("فرشاة أسنان 123 أورال بي")
                        .productImage("https://m.media-amazon.com/images/I/710VJfsMlUL._AC_SL1500_.jpg")
                        .productBrand("فرشاة أسنان")
                        .productUnitPrice(21.00)
                        .productPackPrice(21.00)
                        .productUnit("فرشاة أسنان")
                        .productWeight(1.0)
                        .productRate(5.0)
                        .productDetails("لا توجد تفاصيل")
                        .productActivePrincipal("لا يوجد")
                        .build();
        productRepo.save(product);
//        SubCategoryEntity subCategory =
//                SubCategoryEntity.builder()
//                        .subcategoryTitle("بيوديرما")
//                        .category(category)
//                        .build();
//        subCategoryRepo.save(subCategory);
    }

}