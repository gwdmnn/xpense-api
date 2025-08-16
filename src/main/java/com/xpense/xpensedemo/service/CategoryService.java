package com.xpense.xpensedemo.service;

import com.xpense.xpensedemo.dto.CategoryDTO;
import com.xpense.xpensedemo.model.category.Category;
import com.xpense.xpensedemo.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository repository;

    public CategoryService(CategoryRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Category createCategory(CategoryDTO categoryDTO) {
        if (repository.existsByName(categoryDTO.name())) {
            throw new RuntimeException("Category with name '" + categoryDTO.name() + "' already exists");
        }

        Category category = new Category();
        category.setName(categoryDTO.name());
        return repository.save(category);
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public Optional<Category> getCategoryById(Long id) {
        return repository.findById(id);
    }

    public Optional<Category> getCategoryByName(String name) {
        return repository.findByName(name);
    }

    @Transactional
    public Category updateCategory(Long id, CategoryDTO categoryDTO) {
        Optional<Category> existingCategory = repository.findById(id);
        if (existingCategory.isPresent()) {
            Category category = existingCategory.get();

            Optional<Category> categoryWithSameName = repository.findByName(categoryDTO.name());
            if (categoryWithSameName.isPresent() && !categoryWithSameName.get().getId().equals(id)) {
                throw new RuntimeException("Category with name '" + categoryDTO.name() + "' already exists");
            }

            category.setName(categoryDTO.name());
            return repository.save(category);
        } else {
            throw new RuntimeException("Category not found with id: " + id);
        }
    }

    @Transactional
    public void deleteCategory(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        repository.deleteById(id);
    }
}
