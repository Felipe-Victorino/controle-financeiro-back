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
public class CategoryController implements CrudController<CategoryDTORequest, Long, CategoryDTOResponse>{

    @Autowired
    CategoryService service;

    @Autowired
    ModelMapper mapper;

    @GetMapping("/{userid}")
    public ResponseEntity<List<CategoryDTOResponse>> getAll(@PathVariable Long userid){

        List<Category> categoryList = service.listAllByOwner(userid);

        List<CategoryDTOResponse> response = categoryList.stream()
                .map(category -> mapper.map(category, CategoryDTOResponse.class))
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<CategoryDTOResponse> get(@PathVariable Long id){
        Category catdb = service.findById(id);
        CategoryDTOResponse response = mapper.map(catdb, CategoryDTOResponse.class);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTOResponse> create(@RequestBody @Valid CategoryDTORequest request){

        Category cat = service.insert(this.mapper.map(request, Category.class));

        CategoryDTOResponse response = this.mapper.map(cat, CategoryDTOResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){

        service.delete(id);

        return ResponseEntity.ok().body("Categoria removida");
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTOResponse> update(
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
