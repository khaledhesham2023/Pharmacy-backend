package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.category.GetCategoryContentsResponse;
import com.khaledamin.pharmacy.model.product.GetRelatedProductsRequest;
import com.khaledamin.pharmacy.model.product.GetRelatedProductsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/V1/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductsService productsService;

    @GetMapping("categories/getContents")
    public ResponseEntity<GetCategoryContentsResponse> getContents() {
        return ResponseEntity.ok(categoryService.getCategoryContents());
    }

    @PostMapping("products/relatedProducts/{id}")
    public ResponseEntity<GetRelatedProductsResponse> getRelatedProducts(@PathVariable("id") long categoryId, @RequestBody GetRelatedProductsRequest request) {
        return ResponseEntity.ok(productsService.getRelatedProducts(categoryId, request));
    }
}
