package com.finfin.backend.controller;

import com.finfin.backend.dto.CategoryDTORequest;
import com.finfin.backend.dto.CategoryDTOResponse;
import com.finfin.backend.entity.Category;
import com.finfin.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@CrossOrigin
public class CategoryController {

    @Autowired
    CategoryService service;

    @Autowired
    ModelMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<List<CategoryDTOResponse>> getAllFromUser(@PathVariable Long id){

        List<Category> categoryList = service.listAllByOwner(id);

        List<CategoryDTOResponse> response = categoryList.stream()
                .map(category -> mapper.map(category, CategoryDTOResponse.class))
                .toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTOResponse> createNewCategory(@RequestBody @Valid CategoryDTORequest request){

        Category cat = service.insert(this.mapper.map(request, Category.class));

        CategoryDTOResponse response = this.mapper.map(cat, CategoryDTOResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){

        service.delete(id);

        return ResponseEntity.ok().body("Categoria removida");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTOResponse> updateCategory(
            @PathVariable Long id,
            @RequestBody @Valid CategoryDTORequest request
    ){
        request.setId(id);
        Category cat = service.update(this.mapper.map(request, Category.class));

        CategoryDTOResponse response = this.mapper.map(cat, CategoryDTOResponse.class);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateCategory(@PathVariable Long id){
        Category cat = service.findById(id);
        service.deactivate(cat);
        return ResponseEntity.status(HttpStatus.OK).body("Categoria desativada");
    }

}
