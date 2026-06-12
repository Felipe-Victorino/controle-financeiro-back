package com.finfin.backend.service;

import com.finfin.backend.entity.Category;
import com.finfin.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements BackendService<Category, Long> {

    @Autowired
    CategoryRepository repository;


    @Override
    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
    }

    @Override
    public Category insert(Category category) {
        return repository.save(category);
    }

    @Override
    public void delete(Long id) {
        repository.delete(findById(id));
    }

    @Override
    public Category update(Category category) {
        Category catdb = findById(category.getId());

        catdb.setUser(category.getUser());
        catdb.setName(category.getName());
        catdb.setType(category.getType());
        catdb.setColor(category.getColor());
        catdb.setActive(category.isActive());
        catdb.setIcon(category.getIcon());

        return repository.save(catdb);
    }

    @Override
    public List<Category> listAll() {
        return repository.findAll();
    }
}
