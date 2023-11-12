package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.product.FilterRequest;
import com.khaledamin.pharmacy.model.product.GetRelatedProductsRequest;
import com.khaledamin.pharmacy.model.product.GetRelatedProductsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SubCategoryRepo subCategoryRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public GetRelatedProductsResponse getRelatedProducts(GetRelatedProductsRequest request) {
        Pageable pageOfTenElements = PageRequest.of(request.getPage(), 10);
        long subcategoryId = productRepo.getSubCategoryId(request.getProductId());
        long categoryId = subCategoryRepo.getCategoryId(subcategoryId);
        Page<ProductEntity> relatedProducts = productRepo.getProductsByCategoryNotContainingCurrent(categoryId, pageOfTenElements, request.getProductId());
        return GetRelatedProductsResponse.builder().products(relatedProducts.stream().toList()).build();
    }

    public List<ProductEntity> filterProductsBySubcategory(FilterRequest request) {
        Pageable firstPageOfFiveElements = PageRequest.of(request.getPageNo(), 5);
        Page<ProductEntity> filteredProducts = productRepo.findBySubcategory(subCategoryRepo.findById(request.getSubcategoryId()).orElseThrow(), firstPageOfFiveElements);
        return filteredProducts.stream().toList();
    }
}
