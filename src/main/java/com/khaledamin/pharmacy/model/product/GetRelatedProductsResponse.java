package com.khaledamin.pharmacy.model.product;

import com.khaledamin.pharmacy.model.category.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GetRelatedProductsResponse {
    private List<ProductItem> products;
}
