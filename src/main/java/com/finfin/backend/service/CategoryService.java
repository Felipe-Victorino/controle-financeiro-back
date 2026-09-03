package com.finfin.backend.service;

import com.finfin.backend.dto.CategoryDTOResponse;
import com.finfin.backend.entity.Category;
import com.finfin.backend.repository.CategoryRepository;
import org.jspecify.annotations.NonNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository repository;

    public Category findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("{category.notfound}"));
    }

    public Category insert(@NonNull Category cat) {
        return repository.save(cat);
    }

    public void delete(Long id) {
        repository.delete(findById(id));
    }

    public Category update(@NonNull Category cat) {
        Category catdb = findById(cat.getId());

        catdb.setOwner(cat.getOwner());
        catdb.setName(cat.getName());
        catdb.setType(cat.getType());
        catdb.setColor(cat.getColor());
        catdb.setActive(cat.isActive());
        catdb.setIcon(cat.getIcon());

        return repository.save(catdb);
    }

    public Category updateUser(@NonNull Category cat) {
        Category catdb = findById(cat.getId());
        catdb.setOwner(cat.getOwner());
        return repository.save(catdb);
    }

    public Category updateName(@NonNull Category cat) {
        Category catdb = findById(cat.getId());
        catdb.setName(cat.getName());
        return repository.save(catdb);
    }

    public Category updateType(@NonNull Category cat) {
        Category catdb = findById(cat.getId());
        catdb.setType(cat.getType());
        return repository.save(catdb);
    }

    public Category updateColor(@NonNull Category cat) {
        Category catdb = findById(cat.getId());
        catdb.setColor(cat.getColor());
        return repository.save(catdb);
    }

    public Category updateIcon(@NonNull Category cat) {
        Category catdb = findById(cat.getId());
        catdb.setIcon(cat.getIcon());
        return repository.save(catdb);
    }

    public Category updateActive(@NonNull Category cat) {
        Category catdb = findById(cat.getId());
        catdb.setActive(cat.isActive());
        return repository.save(catdb);
    }

    public void deactivate(Category cat) {
        cat.setActive(false);
        updateActive(cat);
    }


    public List<Category> listAll() {
        return repository.findAll();
    }

    public List<Category> listAllByOwner(Long id) {
        return repository.getCategoriesByOwner(id);
    }
}

