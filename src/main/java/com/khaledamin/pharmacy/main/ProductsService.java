package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.category.ProductItem;
import com.khaledamin.pharmacy.model.product.GetRelatedProductsRequest;
import com.khaledamin.pharmacy.model.product.GetRelatedProductsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SubCategoryRepo subCategoryRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    public GetRelatedProductsResponse getRelatedProducts(long categoryId, GetRelatedProductsRequest request) {
        List<SubCategoryEntity> subCategoryEntities = subCategoryRepo.findByCategory(categoryRepo.findById(categoryId).orElseThrow());
        List<ProductEntity> productEntities;
        List<ProductEntity> relatedProducts = new ArrayList<>();
        List<ProductItem> productItems = new ArrayList<>();
        for (SubCategoryEntity subCategory : subCategoryEntities) {
            productEntities = productRepo.findBySubcategory(subCategory);
            for (ProductEntity product : productEntities) {
                relatedProducts.add(product);
            }
        }
        for (ProductEntity product : relatedProducts) {
            if (request.getId() != product.getProductId()) {
                ProductItem productItem = ProductItem.builder()
                        .productRate(product.getProductRate())
                        .productBrand(product.getProductBrand())
                        .productName(product.getProductName())
                        .productWeight(product.getProductWeight())
                        .productImage(product.getProductImage())
                        .productId(product.getProductId())
                        .productActivePrincipal(product.getProductActivePrincipal())
                        .productDetails(product.getProductDetails())
                        .productUnitPrice(product.getProductUnitPrice())
                        .productUnit(product.getProductUnit())
                        .productPackPrice(product.getProductPackPrice())
                        .manufacturer(product.getManufacturer())
                        .build();
                productItems.add(productItem);
            }
        }
        return GetRelatedProductsResponse.builder().products(productItems).build();

    }
}
