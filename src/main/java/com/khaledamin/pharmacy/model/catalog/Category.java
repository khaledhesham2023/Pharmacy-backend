package com.khaledamin.pharmacy.model.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Category {
    private Long id;
    private String categoryTitle;
    private String categoryImage;
}
