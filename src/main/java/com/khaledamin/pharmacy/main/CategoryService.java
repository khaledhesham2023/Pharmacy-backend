package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.category.GetCategoryContentsResponse;
import com.khaledamin.pharmacy.model.category.CategoryItem;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.model.category.SubCategory;
import com.khaledamin.pharmacy.model.product.ProductsRequest;
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


    public List<CategoryEntity> getCategories() {
        return categoryRepo.findAll();
    }

    public List<SubCategoryEntity> getSubcategoriesByCategory(long categoryId) {
        return subCategoryRepo.findByCategory(categoryRepo.findById(categoryId).orElseThrow());
    }

    public List<ProductEntity> getProducts(ProductsRequest request) {
        Pageable firstPageWithFiveElements = PageRequest.of(request.getPageNo(), 5);
        Page<ProductEntity> allProducts = productRepo.getProductsByCategory(request.getCategoryId(), firstPageWithFiveElements);
        return allProducts.stream().toList();
    }

}