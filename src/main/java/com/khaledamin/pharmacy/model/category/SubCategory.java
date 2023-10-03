package com.khaledamin.pharmacy.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategory {

    private long id;

    private String title;

    private List<ProductItem> products;

}
