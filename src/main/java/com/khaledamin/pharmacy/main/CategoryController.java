package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.category.GetCategoryContentsResponse;
import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.model.product.FilterRequest;
import com.khaledamin.pharmacy.model.product.GetRelatedProductsRequest;
import com.khaledamin.pharmacy.model.product.GetRelatedProductsResponse;
import com.khaledamin.pharmacy.model.product.ProductsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

//    @GetMapping("categories/getContents/{lang}")
//    public ResponseEntity<GetCategoryContentsResponse> getContents(@PathVariable("lang") String language) {
//        return ResponseEntity.ok(categoryService.getCategoryContents(language));
//    }

    @PostMapping("products/relatedProducts")
    public ResponseEntity<GetRelatedProductsResponse> getRelatedProducts(@RequestBody GetRelatedProductsRequest request) {
        return ResponseEntity.ok(productsService.getRelatedProducts(request));
    }

    @GetMapping("categoryItems")
    public ResponseEntity<List<CategoryEntity>> getCategoryItems(){
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("subcategories/{id}")
    public ResponseEntity<List<SubCategoryEntity>> getSubcategoryItems(@PathVariable("id") long categoryId){
        return ResponseEntity.ok(categoryService.getSubcategoriesByCategory(categoryId));
    }
    @PostMapping("products")
    public ResponseEntity<List<ProductEntity>> getProducts(@RequestBody ProductsRequest request){
        return ResponseEntity.ok(categoryService.getProducts(request));
    }
    @PostMapping("findProducts")
    public ResponseEntity<List<ProductEntity>> getProductsBySubcategory(@RequestBody FilterRequest request){
        return ResponseEntity.ok(productsService.filterProductsBySubcategory(request));
    }
}
