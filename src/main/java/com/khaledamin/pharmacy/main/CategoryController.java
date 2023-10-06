package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.category.GetCategoryContentsResponse;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.model.product.GetRelatedProductsRequest;
import com.khaledamin.pharmacy.model.product.GetRelatedProductsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/V1/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductsService productsService;

    @GetMapping("categories/getContents/{lang}")
    public ResponseEntity<GetCategoryContentsResponse> getContents(@PathVariable("lang") String language) {
        return ResponseEntity.ok(categoryService.getCategoryContents(language));
    }

    @PostMapping("products/relatedProducts/{id}")
    public ResponseEntity<GetRelatedProductsResponse> getRelatedProducts(@PathVariable("id") long categoryId, @RequestBody GetRelatedProductsRequest request) {
        return ResponseEntity.ok(productsService.getRelatedProducts(categoryId, request));
    }

    @GetMapping("products/{pageNumber}")
    public ResponseEntity<List<ProductItem>> getProducts(@PathVariable("pageNumber") int pageNumber){
        return ResponseEntity.ok(categoryService.getProducts(pageNumber));
    }
}
