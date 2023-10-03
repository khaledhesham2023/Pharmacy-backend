package com.khaledamin.pharmacy.model.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CartProductItem {
    private long productId;
    private String productName;
    private String productImage;
    private String productBrand;
    private double productUnitPrice;
    private double productPackPrice;
    private String productUnit;
    private double productWeight;
    private double productRate;
    private String productDetails;
    private String productActivePrincipal;
    private int quantity;
}
