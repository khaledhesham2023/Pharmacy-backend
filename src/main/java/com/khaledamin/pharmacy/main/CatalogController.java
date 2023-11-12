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

    @GetMapping("catalog")
    public ResponseEntity<GetCatalogResponse> getCatalog(){
        return ResponseEntity.ok(catalogService.getCatalog());
    }


}
