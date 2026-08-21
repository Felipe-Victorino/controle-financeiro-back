package com.finfin.backend.controller;

import com.finfin.backend.dto.CreateCategoryDTORequest;
import com.finfin.backend.dto.CategoryDTOResponse;
import com.finfin.backend.entity.Category;
import com.finfin.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "localhost:5173")
public class CategoryController {

    @Autowired
    CategoryService service;

    @GetMapping("/{id}")
    public ResponseEntity<List<CategoryDTOResponse>> getAllFromUser(@PathVariable Long id){

        List<CategoryDTOResponse> response = service.listAllByOwner(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTOResponse> createNewCategory(@RequestBody @Valid CreateCategoryDTORequest request){

        Category cat = service.createNewCategoryFromRequest(request);

        CategoryDTOResponse response = new CategoryDTOResponse(
                cat.getId(),
                cat.getName(),
                cat.getColor(),
                cat.getIcon(),
                cat.getType(),
                cat.isActive()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateCateogry(@PathVariable Long id){
        Category cat = service.findById(id);
        service.deactivate(cat);
        return ResponseEntity.status(HttpStatus.OK).body("Categoria desativada");
    }

}
