package com.khaledamin.pharmacy.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CategoryItem {
    private long id;
    private String name;
    private List<SubCategory> subCategories;
}
