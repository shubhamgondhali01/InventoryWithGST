package com.example.gstbilling.controller;

import com.example.gstbilling.model.Category;
import com.example.gstbilling.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Category createCategory(@RequestParam String name, @RequestParam Double gstRate) {
        return categoryService.createCategory(name, gstRate);
    }

    @GetMapping("/{name}")
    public Category getCategory(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
