package com.finfin.backend.controller;

import com.finfin.backend.dto.app.category.CategoryDTORequest;
import com.finfin.backend.dto.app.category.CategoryDTOResponse;
import com.finfin.backend.entity.Category;
import com.finfin.backend.service.CategoryService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
public class CategoryController implements CrudController<CategoryDTORequest, Long, CategoryDTOResponse> {

    @Autowired
    CategoryService service;

    @Autowired
    ModelMapper mapper;

    @GetMapping("")
    public ResponseEntity<List<CategoryDTOResponse>> getAll(Authentication auth) {

        String ownerEmail = auth.getName();
        List<Category> categoryList = service.listAllByOwnerEmail(ownerEmail);

        List<CategoryDTOResponse> response = categoryList.stream()
                .map(category -> mapper.map(category, CategoryDTOResponse.class))
                .toList();

        if (response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTOResponse> get(@PathVariable Long id, Authentication auth) {
        Category catdb = service.findById(id);
        CategoryDTOResponse response = mapper.map(catdb, CategoryDTOResponse.class);

        return ResponseEntity.ok(response);
    }

    @PostMapping("")
    public ResponseEntity<CategoryDTOResponse> create(@RequestBody @Valid CategoryDTORequest request, Authentication auth) {
        String ownerEmail = auth.getName();

        Category cat = service.insert(ownerEmail, this.mapper.map(request, Category.class));

        CategoryDTOResponse response = this.mapper.map(cat, CategoryDTOResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id, Authentication auth) {

        service.delete(id);

        return ResponseEntity.ok().body("Categoria removida");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTOResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid CategoryDTORequest request,
            Authentication auth
    ) {
        Category cat = service.update(this.mapper.map(request, Category.class));

        CategoryDTOResponse response = this.mapper.map(cat, CategoryDTOResponse.class);

        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<String> deactivateCategory(@PathVariable Long id, Authentication auth) {
        Category cat = service.findById(id);
        service.deactivate(cat);
        return ResponseEntity.status(HttpStatus.OK).body("Categoria desativada");
    }

}
