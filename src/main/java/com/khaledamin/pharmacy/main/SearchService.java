package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.SearchRequest;
import com.khaledamin.pharmacy.model.category.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private ProductRepo productRepo;

    public List<ProductEntity> getSearchResults(SearchRequest request) {
        List<ProductEntity> results = productRepo.findByProductNameContainingIgnoreCase(request.getQuery());
        return results;
    }
}
