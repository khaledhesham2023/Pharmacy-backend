package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.SearchRequest;
import com.khaledamin.pharmacy.model.category.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/V1/")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("search")
    public ResponseEntity<List<ProductEntity>> getSearchResults(@RequestBody SearchRequest request){
        return ResponseEntity.ok(searchService.getSearchResults(request));
    }
}
