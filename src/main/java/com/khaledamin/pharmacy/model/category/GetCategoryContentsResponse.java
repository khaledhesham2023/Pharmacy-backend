package com.khaledamin.pharmacy.model.category;

import com.khaledamin.pharmacy.model.catalog.Category;
import com.khaledamin.pharmacy.model.category.CategoryItem;
import com.khaledamin.pharmacy.model.category.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class GetCategoryContentsResponse {

    private List<CategoryItem> categories;

}
