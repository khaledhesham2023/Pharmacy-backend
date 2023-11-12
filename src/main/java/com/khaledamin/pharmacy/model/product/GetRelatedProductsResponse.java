package com.khaledamin.pharmacy.model.product;

import com.khaledamin.pharmacy.main.ProductEntity;
import com.khaledamin.pharmacy.model.category.ProductItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GetRelatedProductsResponse {
    private List<ProductEntity> products;
}
