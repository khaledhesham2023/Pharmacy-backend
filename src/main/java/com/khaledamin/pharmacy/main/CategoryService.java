package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.category.GetCategoryContentsResponse;
import com.khaledamin.pharmacy.model.category.CategoryItem;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.model.category.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private SubCategoryRepo subCategoryRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    public GetCategoryContentsResponse getCategoryContents() {
        List<CategoryItem> categoryItems = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryRepo.findAll();
        List<SubCategoryEntity> subCategoryEntities = subCategoryRepo.findAll();
        List<ProductEntity> productEntities = productRepo.findAll();
        for (CategoryEntity category : categoryEntities) {
            CategoryItem categoryItem = CategoryItem.builder()
                    .id(category.getCategoryId())
                    .name(category.getCategoryName())
                    .subCategories(new ArrayList<>())
                    .build();
            for (SubCategoryEntity subCategoryEntity : subCategoryEntities) {
                if (subCategoryEntity.getCategory().getCategoryId() == categoryItem.getId()) {
                    SubCategory subCategory = SubCategory.builder()
                            .id(subCategoryEntity.getSubcategoryId())
                            .title(subCategoryEntity.getSubcategoryTitle())
                            .products(new ArrayList<>())
                            .build();
                    categoryItem.getSubCategories().add(subCategory);
                    for (ProductEntity product : productEntities) {
                        if (product.getSubcategory().getSubcategoryId() == subCategory.getId()) {
                            ProductItem productItem = ProductItem.builder()
                                    .productId(product.getProductId())
                                    .productName(product.getProductName())
                                    .productDetails(product.getProductDetails())
                                    .manufacturer(product.getManufacturer())
                                    .productRate(product.getProductRate())
                                    .productWeight(product.getProductWeight())
                                    .productBrand(product.getProductBrand())
                                    .productUnit(product.getProductUnit())
                                    .productPackPrice(product.getProductPackPrice())
                                    .productActivePrincipal(product.getProductActivePrincipal())
                                    .productImage(product.getProductImage())
                                    .productUnitPrice(product.getProductUnitPrice())
                                    .build();
                            subCategory.getProducts().add(productItem);
                        }
                    }
                }
            }
            categoryItems.add(categoryItem);
        }
        return GetCategoryContentsResponse.builder()
                .categories(categoryItems)
                .build();
    }
}