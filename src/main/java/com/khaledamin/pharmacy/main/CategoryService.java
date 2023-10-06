package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.category.GetCategoryContentsResponse;
import com.khaledamin.pharmacy.model.category.CategoryItem;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.model.category.SubCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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


    public GetCategoryContentsResponse getCategoryContents(String language) {
        List<CategoryItem> categoryItems = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryRepo.findAll();
        List<SubCategoryEntity> subCategoryEntities = subCategoryRepo.findAll();
        List<ProductEntity> productEntities = productRepo.findAll();
        for (CategoryEntity category : categoryEntities) {
            CategoryItem categoryItem = CategoryItem.builder()
                    .id(category.getCategoryId())
                    .subCategories(new ArrayList<>())
                    .build();
            switch (language){
                case "ar":
                    categoryItem.setName(category.getCategoryNameAr());
                    break;
                case "en":
                    categoryItem.setName(category.getCategoryName());
                    break;
            }
            for (SubCategoryEntity subCategoryEntity : subCategoryEntities) {
                if (subCategoryEntity.getCategory().getCategoryId() == categoryItem.getId()) {
                    SubCategory subCategory = SubCategory.builder()
                            .id(subCategoryEntity.getSubcategoryId())
                            .products(new ArrayList<>())
                            .build();
                    switch (language){
                        case "ar":
                            subCategory.setTitle(subCategoryEntity.getSubcategoryTitleAr());
                            break;
                        case "en":
                            subCategory.setTitle(subCategoryEntity.getSubcategoryTitle());
                            break;
                    }
                    categoryItem.getSubCategories().add(subCategory);
                    for (ProductEntity product : productEntities) {
                        if (product.getSubcategory().getSubcategoryId() == subCategory.getId()) {
                            ProductItem productItem = ProductItem.builder()
                                    .productId(product.getProductId())
                                    .manufacturer(product.getManufacturer())
                                    .productRate(product.getProductRate())
                                    .productWeight(product.getProductWeight())
                                    .productUnit(product.getProductUnit())
                                    .productPackPrice(product.getProductPackPrice())
                                    .productImage(product.getProductImage())
                                    .productUnitPrice(product.getProductUnitPrice())
                                    .build();
                            switch (language){
                                case "ar":
                                    productItem.setProductName(product.getProductNameAr());
                                    productItem.setProductBrand(product.getProductBrandAr());
                                    productItem.setProductDetails(product.getProductDetailsAr());
                                    productItem.setProductActivePrincipal(product.getProductActivePrincipalAr());
                                    break;
                                case "en":
                                    productItem.setProductName(product.getProductName());
                                    productItem.setProductBrand(product.getProductBrand());
                                    productItem.setProductDetails(product.getProductDetails());
                                    productItem.setProductActivePrincipal(product.getProductActivePrincipal());
                                    break;
                            }
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

    public List<ProductItem> getProducts(int pageNo){
        Pageable pageOfNumberOfElements = PageRequest.of(pageNo,5, Sort.by("productId"));
        List<ProductItem> productItems = new ArrayList<>();
        Page<ProductEntity> productEntityPage = productRepo.findAll(pageOfNumberOfElements);
        for (ProductEntity product : productEntityPage){
            ProductItem productItem = ProductItem.builder()
                    .manufacturer(product.getManufacturer())
                    .productId(product.getProductId())
                    .productRate(product.getProductRate())
                    .productImage(product.getProductImage())
                    .productName(product.getProductName())
                    .productWeight(product.getProductWeight())
                    .productBrand(product.getProductBrand())
                    .productDetails(product.getProductDetails())
                    .productUnit(product.getProductUnit())
                    .productActivePrincipal(product.getProductActivePrincipal())
                    .productPackPrice(product.getProductPackPrice())
                    .productUnitPrice(product.getProductUnitPrice())
                    .build();
            productItems.add(productItem);
        }
        return productItems;
    }
}