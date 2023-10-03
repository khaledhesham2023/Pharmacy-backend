package com.khaledamin.pharmacy.main;

import com.khaledamin.pharmacy.model.catalog.GetCatalogResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/V1/")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("catalog/{lang}")
    public ResponseEntity<GetCatalogResponse> getCatalog(@PathVariable String lang){
        return ResponseEntity.ok(catalogService.getCatalog(lang));
    }


}
