package com.example.gstbilling.service;

import com.example.gstbilling.model.Category;
import com.example.gstbilling.repository.CategoryRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(String name, Double gstRate) {
        Category category = new Category();
        category.setName(name);
        category.setGstRate(gstRate);
        return categoryRepository.save(category);
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
    
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
